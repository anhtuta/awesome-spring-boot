package hello.config.aop;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import hello.common.StatusType;
import hello.exception.RestException;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class BootTechAspect {
    /**
     * This checking is just for demo! Tất cả API có annotation RequireNonsense đều phải gửi kèm 1
     * header nonsense là 1 string bắt đầu = giá trị của thuộc tính prefix trong
     * annotation @RequireNonsense.
     * 
     * Note: value = @annotation(requireNons) chứ ko phải @annotation(RequireNonsense) nhé!
     * Dùng @annotation(RequireNonsense) thì ko thêm được tham số thứ 2 (RequireNonsense
     * requireNons)
     * vào method checkNonsense
     */
    @Before(value = "@annotation(requireNons)")
    public void checkNonsense(JoinPoint joinPoint, RequireNonsense requireNons) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();

        String nonsense = request.getHeader("nonsense");
        if (StringUtils.isEmpty(nonsense)) {
            throw new RestException(StatusType.NONSENSE_IS_MISSING);
        }

        String prefix = requireNons.prefix();

        // header "nonsense" gửi từ request phải có prefix được mô tả ở controller, ex:
        // @RequireNonsense(prefix = "demo_prefix_123")
        // Nếu ko chỉ rõ prefix ở method đó, ex: @RequireNonsense,
        // thì mặc định prefix = "tuzaku" (xem bên @interface RequireNonsense)
        if (!nonsense.startsWith(prefix)) {
            throw new RestException(StatusType.INVALID_NONSENSE);
        }
    }

    /**
     * Get prefix of RequireNonsense theo cách khác: ko dùng param requireNons mà dùng reflection
     * Note: khác biệt so với checkNonsense ở trên:
     * - Ở trên dùng param RequireNonsense requireNons, do đó pointCut là @annotation(requireNons)
     * - Ở đây ko có param, do đó pointCut là @annotation(RequireNonsense)
     */
    @AfterReturning(value = "@annotation(RequireNonsense)")
    public void logNonsense(JoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
        Method interfaceMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Method implementationMethod = joinPoint.getTarget().getClass()
                .getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());

        String prefix = null;
        if (implementationMethod.isAnnotationPresent(RequireNonsense.class)) {
            RequireNonsense nonsense = implementationMethod.getAnnotation(RequireNonsense.class);
            prefix = nonsense.prefix();
        }

        // prefix ở đây là giá trị ở bên controller, chứ ko phải giá trị mà request gửi từ header
        log.info("End of method with nonsense that requires prefix: " + prefix);
    }

    /**
     * Định nghĩa pointcut: thực hiện pointcut với tất cả các method trong package hello.controller
     * - Nếu đổi thành "execution(* hello.controller.BookController.*(..))" thì nó chỉ thực hiện
     * các method trong class BookController
     * - Nếu đổi thành "execution(* hello.controller.abc(..))" thì nó chỉ thực hiện với method abc
     */
    @Pointcut("execution(* hello.controller..*.*(..))")
    public void bookControllerPointcut() {}

    /**
     * Ghi lại thời gian thực thi cho tất cả method trong class BookController
     */
    @Around("bookControllerPointcut()")
    public Object executionLog(ProceedingJoinPoint pjp) throws Throwable {
        // Before advice
        long beforeRun = System.currentTimeMillis();

        // Đừng dùng try catch ở đây, vì như vậy nếu có lỗi sẽ bị bắt trong catch,
        // và ko return được về phía user
        Object obj = pjp.proceed();

        // After advice
        long afterRun = System.currentTimeMillis();
        log.info("Calling method: " + pjp.getSignature().getName() + " within "
                + (afterRun - beforeRun) + " ms");

        // Sao phải return kq của hàm proceed nhỉ? Search trên Google nhiều trang nó
        // chả cần return gì (tức là cái method này kiểu void)
        return obj;
    }
}
