package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hello.model.AccessTokenResponse;
import hello.model.SigninRequest;
import hello.service.OAuthService;

@RestController
public class OAuthController {

    @Autowired
    private OAuthService oAuthService;

    @PostMapping("/signin")
    public ResponseEntity<OAuth2AccessToken> getMe(@RequestBody SigninRequest signinRequest) {
        return oAuthService.signin(signinRequest);
    }
}
