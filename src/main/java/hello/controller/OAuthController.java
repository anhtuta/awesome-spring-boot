package hello.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hello.common.Result;
import hello.model.SigninRequest;
import hello.service.OAuthService;

@RestController
public class OAuthController {

    @Autowired
    private OAuthService oAuthService;

    @PostMapping("/signin")
    public ResponseEntity<OAuth2AccessToken> signin(@RequestBody SigninRequest signinRequest) {
        return oAuthService.signin(signinRequest);
    }

    @PostMapping("/signout")
    public Result signout(HttpServletRequest request) {
        return oAuthService.signout(request);
    }
}
