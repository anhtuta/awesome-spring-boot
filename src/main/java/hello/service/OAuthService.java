package hello.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import hello.model.AccessTokenResponse;
import hello.model.SigninRequest;

public interface OAuthService {

    public ResponseEntity<OAuth2AccessToken> signin(SigninRequest signinRequest);

    public boolean checkNonsense(String nonsense);

}
