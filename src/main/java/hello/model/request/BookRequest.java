package hello.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class BookRequest {

    @NotEmpty(message = "title cannot be null or empty")
    private String title;

    @NotEmpty(message = "author cannot be null or empty")
    private String author;

    @NotNull(message = "price cannot be null or empty")
    private Integer price;

    @NotNull(message = "categoryId cannot be null or empty")
    private List<Integer> categoryIds;
}
