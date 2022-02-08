package hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hello.common.Result;

@RestController
@RequestMapping("/public-api")
public class PublicController {

    @GetMapping(value = "hello-world")
    public Result helloWorld() {
        return new Result().successRes("Hello world!");
    }
}
