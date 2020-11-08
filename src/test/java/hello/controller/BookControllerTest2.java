package hello.controller;

import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import hello.common.Result;
import hello.common.StatusType;
import hello.entity.Book;

public class BookControllerTest2 extends AbstractControllerTest {

    @Test
    @SuppressWarnings("unchecked")
    @WithMockUser(username = "mockUser", authorities = "USER")
    public void getAllBookTest() throws Exception {
        String uri = "/api/book/all";
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        Assert.assertEquals(200, status);

        String content = response.getContentAsString();
        Result result = super.mapFromJson(content, Result.class);
        Assert.assertEquals(StatusType.SUCCESS.getCode(), result.getCode());

        List<Book> bookList = (List<Book>) result.getData();
        Assert.assertTrue(bookList.size() > 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    @WithMockUser(username = "mockUser", authorities = "USER")
    public void getBooksTest_existData() throws Exception {
        String uri = "/api/book?page=0&size=10";
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        Assert.assertEquals(200, status);

        String content = response.getContentAsString();
        Result result = super.mapFromJson(content, Result.class);
        Assert.assertEquals(StatusType.SUCCESS.getCode(), result.getCode());

        Map<Object, Object> data = (Map<Object, Object>) result.getData();
        Assert.assertTrue(Integer.valueOf(data.get("totalElements") + "") > 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    @WithMockUser(username = "mockUser", authorities = "USER")
    public void getBooksTest_noData() throws Exception {
        String uri = "/api/book?page=0&size=10&searchText=thereisnobookcontainsthistext";
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        Assert.assertEquals(200, status);

        String content = response.getContentAsString();
        Result result = super.mapFromJson(content, Result.class);
        Assert.assertEquals(StatusType.SUCCESS.getCode(), result.getCode());

        Map<Object, Object> data = (Map<Object, Object>) result.getData();
        Assert.assertTrue(Integer.valueOf(data.get("totalElements") + "") == 0);
    }
}
