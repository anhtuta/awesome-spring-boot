package hello.config.aop;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import hello.common.StatusType;
import hello.exception.RestException;

@Component
@Aspect
public class BootTechAspect {
    /**
     * This checking is just for demo! Tất cả API có annotation RequireNonsense đều phải gửi kèm 1
     * header nonsense là 1 string bắt đầu = giá trị của thuộc tính prefix trong annotation @RequireNonsense.
     * Giá trị mặc định đó = 'tuzaku'!
     * Note: value = @annotation(requireNons) chứ ko phải @annotation(RequireNonsense) nhé!
     * Dùng @annotation(RequireNonsense) thì ko thêm được tham số thứ 2 (RequireNonsense requireNons)
     * vào method checkNonsense
     */
    @Before(value = "@annotation(requireNons)")
    public void checkNonsense(JoinPoint joinPoint, RequireNonsense requireNons) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String nonsense = request.getHeader("nonsense");
        if (StringUtils.isEmpty(nonsense)) {
            throw new RestException(StatusType.NONSENSE_IS_MISSING);
        }

        String prefix = requireNons.prefix();
        if (prefix.equals("")) {
            prefix = "tuzaku";
        }
        if (!nonsense.startsWith(prefix)) {
            throw new RestException(StatusType.INVALID_NONSENSE);
        }
    }

    /**
     * Định nghĩa pointcut: thực hiện pointcut với tất cả các method trong class BookController.
     * Nếu đổi thành "execution(* hello.controller.abc(..))" thì nó chỉ thực hiện với method abc
     * @return 
     */
    @Pointcut("execution(* hello.controller.BookController.*(..))")
    public void bookControllerPointcut() {
    }

    /**
     * Ghi lại thời gian thực thi cho tất cả method trong class BookController
     * @param pjp
     * @return
     * @throws Throwable
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
        System.out.println("Calling method: " + pjp.getSignature().getName() + " within "
                + (afterRun - beforeRun) + " ms");

        // Sao phải return kq của hàm proceed nhỉ? Search trên Google nhiều trang nó
        // chả cần return gì (tức là cái method này kiểu void)
        return obj;
    }
}
