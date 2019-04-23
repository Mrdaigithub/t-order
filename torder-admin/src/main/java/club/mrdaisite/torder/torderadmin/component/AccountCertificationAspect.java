package club.mrdaisite.torder.torderadmin.component;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * AccountCertificationAspect
 *
 * @author dai
 * @date 2019/04/10
 */
@Aspect
@Component
@Order(3)
public class AccountCertificationAspect {
    @Pointcut("execution(public * club.mrdaisite.torder.torderadmin.controller.*.*(..))")
    public void accountCertification() {
    }

    @Around("accountCertification()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String message;
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if ((message = getErrorsMessage(result)) != null) {
                    return new CommonResult().badRequest(message);
                }
            }
        }
        return joinPoint.proceed();
    }
}
