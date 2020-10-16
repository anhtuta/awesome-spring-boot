package hello.service;

import org.springframework.data.domain.Pageable;
import hello.common.Result;

public interface BookService {

    public Result getBooks(Pageable pageable, String searchText);

    public Result getAllBooks();

    public Result getBook(int id);

}
