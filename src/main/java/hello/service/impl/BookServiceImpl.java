package hello.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import hello.common.ListRes;
import hello.common.Result;
import hello.common.StatusType;
import hello.entity.Book;
import hello.entity.Category;
import hello.exception.RestException;
import hello.model.request.BookRequest;
import hello.predicate.BookPredicate;
import hello.repository.BookRepository;
import hello.repository.CategoryRepository;
import hello.service.BookService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // This one has error
    @Override
    public Result getBooks(Pageable pageable, String searchText) {
        log.debug("BookServiceImpl.getBooks");
        Result result = new Result();
        Sort sort;
        if (pageable.getSort().isUnsorted()) {
            sort = Sort.by(Direction.DESC, "id");
        } else {
            sort = pageable.getSort();
        }
        PageRequest pageRequest =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Book> bookPage =
                bookRepository.findAll(BookPredicate.getPredicate(searchText), pageRequest);
        List<Book> bookList = bookPage.getContent();
        long totalElements = bookPage.getTotalElements();
        int totalPages = (int) Math.ceil(totalElements * 1.0 / pageable.getPageSize());

        result.successRes(new ListRes<Book>(bookList, totalElements, totalPages));
        return result;
    }

    @Override
    public Result getBookDetails(Pageable pageable, String searchText) {
        log.debug("BookServiceImpl.getBookDetails");
        Result result = new Result();
        Sort sort;
        if (pageable.getSort().isUnsorted()) {
            sort = Sort.by(Direction.DESC, "id");
        } else {
            sort = pageable.getSort();
        }
        PageRequest pageRequest =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Map<String, Object>> bookPage = bookRepository.getBookDetails(pageRequest);
        List<Map<String, Object>> bookList = bookPage.getContent();

        long totalElements = bookPage.getTotalElements();
        int totalPages = (int) Math.ceil(totalElements * 1.0 / pageable.getPageSize());

        result.successRes(new ListRes<Map<String, Object>>(bookList, totalElements, totalPages));
        return result;
    }

    @Override
    public Result getAllBooks() {
        log.debug("BookServiceImpl.getAllBooks");
        Result result = new Result();
        List<Book> bookList = bookRepository.findAll();
        result.setData(bookList);
        result.setStatus(StatusType.SUCCESS);
        return result;
    }

    @Override
    public Result getBook(int id) {
        log.debug("BookServiceImpl.getBook");
        Result result = new Result();
        Optional<Book> bookOp = bookRepository.findById(id);
        if (bookOp.isPresent()) {
            result.setData(bookOp.get());
            result.setStatus(StatusType.SUCCESS);
        } else {
            result.setStatus(StatusType.NO_DATA);
        }
        return result;
    }

    @Override
    @Transactional
    public Result createBook(BookRequest bookRequest) {
        // Check if exist categories
        List<Integer> categoryIds = bookRequest.getCategoryIds();
        Integer isExist = categoryRepository.checkExist(categoryIds);
        if (isExist == null) {
            throw new RestException(StatusType.CATEGORY_NOT_FOUND);
        }

        // Create book
        Book newBook = new Book();
        BeanUtils.copyProperties(bookRequest, newBook);
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        newBook.setCategories(categories);
        bookRepository.save(newBook);
        return new Result().successRes(null);
    }

    @Override
    public Result updateBook(int id, BookRequest bookRequest) {
        // Check if exist book
        Optional<Book> bookOp = bookRepository.findById(id);
        if (!bookOp.isPresent()) {
            throw new RestException(StatusType.BOOK_NOT_FOUND);
        }

        // Check if exist categories
        List<Integer> categoryIds = bookRequest.getCategoryIds();
        Integer isExist = categoryRepository.checkExist(categoryIds);
        if (isExist == null) {
            throw new RestException(StatusType.CATEGORY_NOT_FOUND);
        }

        Book updateBook = bookOp.get();
        BeanUtils.copyProperties(bookRequest, updateBook);
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        updateBook.setCategories(categories);
        bookRepository.save(updateBook);
        return new Result().successRes(null);
    }

    @Override
    public Result deleteBook(int id) {
        Optional<Book> bookOp = bookRepository.findById(id);
        if (!bookOp.isPresent()) {
            throw new RestException(StatusType.BOOK_NOT_FOUND);
        }

        bookRepository.delete(bookOp.get());
        return new Result().successRes(null);
    }
}
