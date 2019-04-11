package club.mrdaisite.torder.torderadmin.component;

import club.mrdaisite.torder.torderadmin.util.LoggerUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ExceptionHandlerAspect
 *
 * @author dai
 * @date 2019/03/25
 */
@Component
@Aspect
@Order(1)
public class ExceptionHandlerAspect {
    @Pointcut("execution(public * club.mrdaisite.torder.torderadmin.component.GlobalExceptionHandler.*(..))")
    public void exceptionHandler() {
    }

    @Before("exceptionHandler()")
    public void doBefore(JoinPoint joinpoint) {
        Exception exception = (Exception) (joinpoint.getArgs())[1];
        LoggerUtil.logger.error(exception.getClass().getName() + ": " + exception.getMessage());
    }
}
