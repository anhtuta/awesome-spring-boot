package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import hello.common.Result;
import hello.service.OAuthService;

@RestController
public class OAuthController {

    @Autowired
    private OAuthService oAuthService;

    @PostMapping("/signin")
    public Result getMe() {
        return oAuthService.signin();
    }
}
