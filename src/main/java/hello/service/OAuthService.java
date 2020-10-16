package hello.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import hello.model.SigninRequest;

public interface OAuthService {

    public ResponseEntity<OAuth2AccessToken> signin(SigninRequest signinRequest);

}
