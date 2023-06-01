# AwesomeSpringBoot

Spring Boot pratice with lots of technologies

# Swagger local

http://localhost:9010/asb/swagger-ui.html

Sign in using accounts: admin/1111, att/1111, storemg/1111, bookmg/1111

# How to use

## Run local without docker

- First, run Redis if bean TokenStore using `RedisTokenStore` instead of `InMemoryTokenStore`
- Then, get access token via this API: http://localhost:9010/boot-tech/signin. All default accounts (in first run using flyway) have password = "1111"
- Use acccess token to call APIs

## Run local using docker

- Create volume for MySQL
  ```
  docker volume create abs_mysql_data
  docker volume create abs_mysql_config
  ```
- Create network for MySQL: `docker network create abs_mysqlnet`
- Run MySQL in a container and attach to the volumes and network we created above
  ```
  docker run -it --rm -d -v abs_mysql_data:/var/lib/mysql \
  -v abs_mysql_config:/etc/mysql/conf.d \
  --network abs_mysqlnet \
  --name abs_mysqlserver \
  -e MYSQL_USER=abs -e MYSQL_PASSWORD=abs1111 \
  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=abs \
  -p 3307:3306 mysql:8.0
  ```
  Now, in Spring config, we need to connect to `abs_mysqlserver` instead of `localhost:3306`. View [application-docker.yml](./src/main/resources/application-docker.yml) for more detail
- Build image (if you want to remove previous build, run `docker image rm abs:1.0.0` first): `docker build --tag abs:1.0.0 .`
- Run container, also attach to the network we created above:
  ```
  docker run --rm -d \
  --name abs_server \
  --network abs_mysqlnet \
  -p 9010:9010 abs:1.0.0
  ```

Done rồi đó, check thử image vừa được tạo:

```bash
➜  awesome-spring-boot git:(master) ✗ docker images
REPOSITORY                                 TAG           IMAGE ID       CREATED         SIZE
abs                                        1.0.0         54c857f8b2af   4 minutes ago   369MB
mysql                                      8.0           fe893ca74649   7 days ago      592MB
```

Ta thấy có image của backend và của mysql. Check thử containers xem:

```bash
➜  awesome-spring-boot git:(master) ✗ docker ps
CONTAINER ID   IMAGE          COMMAND                  CREATED             STATUS             PORTS                               NAMES
41636579026b   abs:1.0.0      "./mvnw spring-boot:…"   6 minutes ago       Up 6 minutes       0.0.0.0:9010->9010/tcp              abs_server
e8b77575bbb9   mysql:8.0      "docker-entrypoint.s…"   About an hour ago   Up About an hour   33060/tcp, 0.0.0.0:3307->3306/tcp   abs_mysqlserver
```

Giờ vào swagger mà xem. Thử upload rồi download file xem có được ko. Đầu tiên phải login để lấy access token (acc nào cũng được), sau đó test api upload. Vào bên trong container để xem có upload được ko (note: dấu `#` là đang dùng account `root` nhé):

```bash
➜  awesome-spring-boot git:(master) ✗ docker exec -it 41636579026b /bin/sh
# pwd
/app
# ls -la
total 44
drwxr-xr-x 1 root root  4096 Jun  1 09:07 .
drwxr-xr-x 1 root root  4096 Jun  1 09:06 ..
drwxr-xr-x 2 root root  4096 Jun  1 09:07 abs-upload
drwxr-xr-x 3 root root  4096 Jun  1 06:48 .mvn
-rwxr-xr-x 1 root root 10284 May 31 17:34 mvnw
-rw-r--r-- 1 root root  6455 May 31 16:03 pom.xml
drwxr-xr-x 4 root root  4096 Jun  1 09:06 src
drwxr-xr-x 7 root root  4096 Jun  1 09:06 target
# cd abs-upload
# ls -la
total 1324
drwxr-xr-x 2 root root    4096 Jun  1 09:07 .
drwxr-xr-x 1 root root    4096 Jun  1 09:07 ..
-rw-r--r-- 1 root root 1344144 Jun  1 09:07 IMG_7921_1685610467081.JPG
```

Oke, vậy là upload được rồi

Ref: https://docs.docker.com/language/java/

# Technologies are used

- Swagger
- Flyway
- JPA/Hibernate
- Lombok
- jxls
- Spring Security, OAuth2
- RESTful API

# Setup https

- Using this command to generate keystore (with PKCS12 format):

```
keytool -genkeypair -alias tuzaku -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 -storepass tzk@20200923
```

# Refs:

- https://www.thomasvitale.com/https-spring-boot-ssl-certificate/
- https://www.baeldung.com/rest-api-search-language-spring-data-querydsl
- https://www.baeldung.com/spring-security-integration-tests

# JPA rất ngu!

## KHÔNG dùng được JpaRepository với class ko phải entity

Nếu dùng:

```java
public interface BookRepository extends JpaRepository<Book, Integer>
```

Thì `class Book` phải là 1 entity (`@Entity`)

Dùng query map sang object ko phải entity thì KHÔNG được:

```java
 @Query(nativeQuery = true,
        value = "SELECT "
                + "    b.id, "
                + "    b.title, "
                + "    b.author, "
                + "    b.price, "
                + "    b.created_date, "
                + "    b.modified_date, "
                + "    GROUP_CONCAT(c.id SEPARATOR ';') AS category_id_str, "
                + "    GROUP_CONCAT(c.name SEPARATOR ';') AS category_name_str "
                + "FROM book b "
                + "LEFT JOIN book_category bc "
                + "    ON b.id = bc.book_id "
                + "INNER JOIN category c "
                + "    ON bc.category_id = c.id "
                + "GROUP BY b.id "
                + "ORDER BY b.id",
        countQuery = "SELECT count(id) FROM book")
Page<BookDetail> getBookDetails(Pageable pageable);
```

(Giả sử như có class BookDetail với các field tương ứng với các cột trong query trên! Vẫn KHÔNG dùng được!)

Tất nhiên có cách, chẳng hạn dùng `@NamedNativeQuery` và `@SqlResultSetMapping`, nhưng trông chả đẹp mắt tý nào!!! Tham khảo các link bên dưới

Ref:

- https://stackoverflow.com/questions/55513776/create-spring-repository-without-entity
- https://stackoverflow.com/questions/29082749/spring-data-jpa-map-the-native-query-result-to-non-entity-pojo
- https://stackoverflow.com/questions/64762080/how-to-map-sql-native-query-result-into-dto-in-spring-jpa-repository

## KHÔNG select được cột @Transient

Giả sử 1 Entity có cột:

```java
@Transient
private String categoryIdStr;
```

Thì query cột đó, data vẫn null!

```java
@Query(nativeQuery = true,
        value = "SELECT "
                + "    b.id, "
                + "    b.title, "
                + "    b.author, "
                + "    b.price, "
                + "    b.created_date, "
                + "    b.modified_date, "
                + "    GROUP_CONCAT(c.id SEPARATOR ';') AS category_id_str, "	// NULL!
                + "    GROUP_CONCAT(c.name SEPARATOR ';') AS category_name_str "
                + "FROM book b "
                + "LEFT JOIN book_category bc "
                + "    ON b.id = bc.book_id "
                + "INNER JOIN category c "
                + "    ON bc.category_id = c.id "
                + "GROUP BY b.id "
                + "ORDER BY b.id",
        countQuery = "SELECT count(id) FROM book")
Page<Book> getBookDetails(Pageable pageable);
```

## Last pagination JPA doesn't call count query

Thật vậy, nếu có 1 API phân trang, giả sử totalRow = 34, pageSize = 10, tức là có 4 page. Thì page cuối cùng (pageNum=3 & pageSize=10) nó sẽ ko gọi countQuery nữa! (Đã test với trường hợp `nativeQuery = true`). Có lẽ nó tự tính được totalRow, tính dễ mà! Một sự tối ưu nho nhỏ!
