package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hello.common.ApiPageable;
import hello.common.Result;
import hello.config.aop.RequireNonsense;
import hello.service.BookService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/book")
//@PreAuthorize("hasAuthority('USER')")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @ApiPageable
    public Result getBooks(
            @ApiIgnore Pageable pageable,
            @RequestParam(required = false) String searchText) {
        return bookService.getBooks(pageable, searchText);
    }

    @GetMapping(value = "/all")
    public Result getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(value = "/{id}")
    @RequireNonsense
    public Result getBook(@PathVariable("id") int id) {
        return bookService.getBook(id);
    }
}
