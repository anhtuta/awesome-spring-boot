package hello.service;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import hello.common.Result;
import hello.model.request.BookRequest;

public interface BookService {

    public Result getBooks(Pageable pageable, String searchText);

    public Result getAllBooks();

    public Result getBook(int id);

    public Result createBook(BookRequest bookRequest);

    public Result updateBook(int id, BookRequest bookRequest);

    public Result deleteBook(int id);

}
