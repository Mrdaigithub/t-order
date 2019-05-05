package club.mrdaisite.torder.torderadmin.component;

import club.mrdaisite.torder.common.api.CommonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * BindingResultAspect
 *
 * @author dai
 * @date 2019/04/10
 */
@Aspect
@Component
@Order(2)
public class BindingResultAspect {
    @Pointcut("execution(public * club.mrdaisite.torder.torderadmin.controller.*.*(..))")
    public void bingingResult() {
    }

    @Around("bingingResult()")
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

    private String getErrorsMessage(BindingResult result) {
        StringBuilder message = new StringBuilder();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(",");
            }
            message.deleteCharAt(message.length() - 1);
            return message.toString();
        }
        return null;
    }
}
