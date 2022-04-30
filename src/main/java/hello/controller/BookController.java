package hello.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hello.common.ApiPageable;
import hello.common.Result;
import hello.common.StatusType;
import hello.config.aop.RequireNonsense;
import hello.exception.RestException;
import hello.model.request.BookRequest;
import hello.service.BookService;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/book")
@Slf4j
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @ApiPageable
    @PreAuthorize("hasAuthority('USER')")
    public Result getBooks(@ApiIgnore Pageable pageable,
            @RequestParam(required = false) String searchText) {
        return bookService.getBooks(pageable, searchText);
    }

    @GetMapping("/detail")
    @ApiPageable
    @PreAuthorize("hasAuthority('USER')")
    public Result getBookDetails(@ApiIgnore Pageable pageable,
            @RequestParam(required = false) String searchText) {
        return bookService.getBookDetails(pageable, searchText);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('USER')")
    public Result getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(value = "/{id}")
    @RequireNonsense
    @PreAuthorize("hasAuthority('USER')")
    public Result getBook(@PathVariable("id") int id) {
        return bookService.getBook(id);
    }

    @GetMapping(value = "/by-id/{id}")
    @RequireNonsense(prefix = "hehe")
    @PreAuthorize("hasAuthority('USER')")
    public Result getBookById(@PathVariable("id") int id) {
        return bookService.getBook(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BOOK_MANAGER')")
    public Result createBook(@Valid @RequestBody BookRequest bookRequest,
            BindingResult bindingResult) {
        List<String> errorList = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            log.error(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            errorList.add(fieldError.getDefaultMessage());
        });
        if (errorList.size() != 0) {
            throw new RestException(StatusType.BAD_REQUEST, String.join(", ", errorList));
        }
        return bookService.createBook(bookRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('BOOK_MANAGER')")
    public Result updateBook(@PathVariable("id") int id,
            @Valid @RequestBody BookRequest bookRequest, BindingResult bindingResult) {
        bindingResult.getFieldErrors().forEach(fieldError -> {
            log.error(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            throw new RestException(StatusType.BAD_REQUEST, fieldError.getDefaultMessage());
        });
        return bookService.updateBook(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BOOK_MANAGER')")
    public Result updateBook(@PathVariable("id") int id) {
        return bookService.deleteBook(id);
    }
}
