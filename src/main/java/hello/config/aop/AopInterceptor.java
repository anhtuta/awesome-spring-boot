package hello.config.aop;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import hello.common.StatusType;
import hello.exception.RestException;
import hello.service.OAuthService;

@Component
@Aspect
public class AopInterceptor {

    @Autowired
    private OAuthService oAuthService;

    @Before(value = "@within(RequireNonsense) || @annotation(RequireNonsense)")
    public void checkNonsense(JoinPoint joinPoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        String nonsense = request.getHeader("nonsense");
        if (StringUtils.isEmpty(nonsense)) {
            throw new RestException(StatusType.NONSENSE_IS_MISSING);
        }

        if (!oAuthService.checkNonsense(nonsense)) {
            throw new RestException(StatusType.INVALID_NONSENSE);
        }
    }
}
