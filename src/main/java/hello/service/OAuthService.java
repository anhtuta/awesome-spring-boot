package hello.service;

import hello.common.Result;
import hello.model.request.SigninRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface OAuthService {

    public ResponseEntity<OAuth2AccessToken> signin(SigninRequest signinRequest);

    public Result signout(HttpServletRequest request);

}
