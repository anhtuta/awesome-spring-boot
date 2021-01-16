package hello.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import hello.common.ListRes;
import hello.common.Result;
import hello.common.StatusType;
import hello.entity.Book;
import hello.predicate.BookPredicate;
import hello.repository.BookRepository;
import hello.service.BookService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Result getBooks(Pageable pageable, String searchText) {
        Result result = new Result();
        log.debug("BookServiceImpl.getBooks");

        try {
            Page<Book> bookPage =
                    bookRepository.findAll(BookPredicate.getPredicate(searchText), pageable);
            List<Book> bookList = bookPage.getContent();
            long totalElements = bookPage.getTotalElements();
            int totalPages = (int) Math.ceil(totalElements * 1.0 / pageable.getPageSize());

            result.successRes(new ListRes<Book>(bookList, totalElements, totalPages));
        } catch (Exception e) {
            log.error("Fail getAllBooks: ", e);
            result.setStatus(StatusType.FAIL, e.getMessage());
        }
        return result;
    }

    public Result getAllBooks() {
        Result result = new Result();
        log.debug("BookServiceImpl.getAllBooks");

        try {
            List<Book> bookList = bookRepository.findAll();
            result.setData(bookList);
            result.setStatus(StatusType.SUCCESS);
        } catch (Exception e) {
            log.error("Fail getAllBooks: ", e);
            result.setStatus(StatusType.FAIL, e.getMessage());
        }
        return result;
    }

    public Result getBook(int id) {
        Result result = new Result();
        log.debug("BookServiceImpl.getBook");

        try {
            Optional<Book> bookOp = bookRepository.findById(id);
            if (bookOp.isPresent()) {
                result.setData(bookOp.get());
                result.setStatus(StatusType.SUCCESS);
            } else {
                result.setStatus(StatusType.NO_DATA);
            }
        } catch (Exception e) {
            log.error("Fail getBook: ", e);
            result.setStatus(StatusType.FAIL, e.getMessage());
        }
        return result;
    }
}
