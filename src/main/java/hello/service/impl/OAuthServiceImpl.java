package hello.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Service;
import hello.common.Constants;
import hello.common.Result;
import hello.common.StatusType;
import hello.config.security.AuthorizationServerConfig;
import hello.exception.RestException;
import hello.model.SigninRequest;
import hello.service.OAuthService;

@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    private AuthorizationServerConfig authConfig;

    @Autowired
    private ConsumerTokenServices tokenServices;

    /**
     * Ref: org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
     */
    @Override
    public ResponseEntity<OAuth2AccessToken> signin(SigninRequest signinRequest) {
        Collection<String> scopes = Arrays.asList("read", "write");
        final String grantType = "password";
        Map<String, String> reqParams = new HashMap<>(3);

        reqParams.put("username", signinRequest.getUsername());
        reqParams.put("password", signinRequest.getPassword());
        reqParams.put("grant_type", grantType);

        TokenRequest tokenRequest =
                new TokenRequest(reqParams, Constants.CLIENT_ID, scopes, grantType);
        OAuth2AccessToken oAuth2AccessToken =
                authConfig.getTokenGranter().grant(grantType, tokenRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<OAuth2AccessToken>(oAuth2AccessToken, headers, HttpStatus.OK);
    }

    @Override
    public Result signout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        int bearLen = "bearer".length();
        Result result = new Result();

        if (authorization != null && authorization.length() > bearLen) {
            String bearer = authorization.substring(0, bearLen);
            if ("bearer".equalsIgnoreCase(bearer)) {
                String accessToken = (authorization.substring(bearLen)).trim();
                if (tokenServices.revokeToken(accessToken)) {
                    result.successRes(null);
                } else {
                    result.failRes(null);
                }
            }
        } else {
            throw new RestException(StatusType.INVALID_AUTHORIZATION_HEADER);
        }

        return result;
    }

}
