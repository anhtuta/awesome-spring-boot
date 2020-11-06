package hello.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
import hello.common.Result;
import hello.common.StatusType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {

    // @Autowired
    // private MockMvc mvc;

    @Autowired
    private BookController bookController;

    @Test
    public void getAllBooks_p() {
        Result result = bookController.getAllBooks();
        Assert.assertEquals(StatusType.SUCCESS.getCode(), result.getCode());
    }

    @Test
    public void getAllBooks_n() {
        Result result = bookController.getBook(100000);
        Assert.assertEquals(StatusType.NO_DATA.getCode(), result.getCode());
    }
}
