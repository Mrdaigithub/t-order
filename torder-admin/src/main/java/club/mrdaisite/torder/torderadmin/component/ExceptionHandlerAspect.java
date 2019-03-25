package club.mrdaisite.torder.torderadmin.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * club.mrdaisite.torder.torderadmin.component.GlobalExceptionHandler.*(..))")
    public void exceptionHandler() {
    }

    @Before("exceptionHandler()")
    public void doBefore(JoinPoint joinpoint) {
        Exception exception = (Exception) (joinpoint.getArgs())[1];
        LOGGER.error(exception.getClass().getName() + ": " + exception.getMessage());
    }
}
