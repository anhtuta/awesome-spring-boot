package hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class TokenConfig {

    // Dùng 2 bean cho từng profile khác nhau mà Spring ko nhận ra bean TokenStore? Tại sao
    // @Profile("prd")
    @Bean
    public TokenStore tokenStore() {
    return new InMemoryTokenStore();
    }

    // @Profile("local")
    // @Bean
    // public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
    //     return new RedisTokenStore(redisConnectionFactory);
    // }
}
