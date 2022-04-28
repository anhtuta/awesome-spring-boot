package hello.repository;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Predicate;
import hello.entity.Book;

@Repository
public interface BookRepository
        extends JpaRepository<Book, Integer>, QuerydslPredicateExecutor<Book> {

    @Override
    Page<Book> findAll(Predicate predicate, Pageable pageable);

    // Note: KHÔNG thể map sang BookDetail, xem readme!
    // Đôi khi dùng nativeQuery thì countQuery có thể sẽ tối ưu hơn, ko cần phải
    // count từ sub-query bao gồm nhiều JOIN phức tạp nữa, count trên 1 bảng là đủ
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
    Page<Map<String, Object>> getBookDetails(Pageable pageable);

}
