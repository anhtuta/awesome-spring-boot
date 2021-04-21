package hello.model.response;

import java.util.Set;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import lombok.Getter;
import lombok.Setter;

/**
 * Not use!
 * @author Anhtu
 */
@Getter
@Setter
public class AccessTokenResponse {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private Integer expires_in;
    private String scope;

    public AccessTokenResponse(OAuth2AccessToken oAuth2AccessToken) {
        super();
        this.access_token = oAuth2AccessToken.getValue();
        this.token_type = oAuth2AccessToken.getTokenType();
        this.refresh_token = oAuth2AccessToken.getRefreshToken().getValue();
        this.expires_in = oAuth2AccessToken.getExpiresIn();
        this.scope = getScopes(oAuth2AccessToken.getScope());
    }

    private String getScopes(Set<String> scopes) {
        StringBuilder builder = new StringBuilder("");
        for (String scope : scopes) {
            builder.append(scope).append(" ");
        }
        if (builder.length() > 0) {
            return builder.substring(0, builder.length() - 1);
        }
        return builder.toString();
    }
}
