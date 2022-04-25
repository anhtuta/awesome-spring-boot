package hello.model.response;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.entity.Book;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookDetail extends Book {

    @JsonIgnore
    private String categoryStr;

    @Transient
    private List<String> categoryNames;

}
