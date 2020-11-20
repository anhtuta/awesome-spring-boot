package hello.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import hello.common.Result;
import hello.model.SigninRequest;

public interface OAuthService {

    public ResponseEntity<OAuth2AccessToken> signin(SigninRequest signinRequest);

    public Result signout(HttpServletRequest request);

}
