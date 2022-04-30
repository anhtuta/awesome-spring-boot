package hello.model.response;

import java.util.Date;
import java.util.List;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

// JPA yêu cầu 1 entity mới có thể dùng được JpaRepository,
// do đó tạo 1 model rồi map từ native query sang model này là KHÔNG được!
// This class is currently UNUSED!
@Getter
@Setter
public class BookDetail {

    private Integer id;
    private String title;
    private String author;
    private Integer price;
    private Date createdDate;
    private Date modifiedDate;

    @JsonIgnore
    private String categoryIdStr;

    @JsonIgnore
    private String categoryNameStr;

    @Transient
    private List<String> categoryNames;

}
