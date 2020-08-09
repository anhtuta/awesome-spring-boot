package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hello.common.Result;
import hello.service.BookService;

@RestController
@RequestMapping("/api/book")
@PreAuthorize("hasAuthority('USER')")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Result getBooks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookService.getBooks(pageable);
    }

    @GetMapping(value = "/all")
    public Result getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(value = "/{id}")
    public Result getBook(@PathVariable("id") int id) {
        return bookService.getBook(id);
    }
}
