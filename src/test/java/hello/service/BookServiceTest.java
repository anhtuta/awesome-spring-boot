package hello.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import hello.common.ListRes;
import hello.common.Result;
import hello.common.StatusType;
import hello.entity.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void getBooks_p() {
        Pageable pageable = PageRequest.of(0, 10);
        Result result = bookService.getBooks(pageable, null);
        Assert.assertEquals(StatusType.SUCCESS.getCode(), result.getCode());
    }

    @Test
    public void getBooks_n() {
        Pageable pageable = PageRequest.of(0, 10);
        Result result = bookService.getBooks(pageable, "thereisnobookwithtextlikethis");
        @SuppressWarnings("unchecked")
        ListRes<Book> resList = (ListRes<Book>) result.getData();
        Assert.assertEquals(StatusType.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(0, resList.getTotalElements());
    }
}
