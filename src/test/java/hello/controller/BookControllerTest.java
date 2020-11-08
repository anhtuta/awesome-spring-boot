package hello.controller;

import org.junit.Assert;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import hello.common.Result;
import hello.common.StatusType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private BookController bookController;

    // This test is okay
    //@Test
    @WithMockUser(username = "mockUser", authorities = "USER")
    public void getAllBooks_p() {
        Result result = bookController.getAllBooks();
        Assert.assertEquals(StatusType.SUCCESS.getCode(), result.getCode());
    }

    // But this test: we can't set header for request!
    //@Test
    @WithMockUser(username = "mockUser", authorities = "USER")
    public void getBooks_missingNonsenseHeader() {
        Result result = bookController.getBook(1);
        Assert.assertEquals(StatusType.NO_DATA.getCode(), result.getCode());
    }
}
