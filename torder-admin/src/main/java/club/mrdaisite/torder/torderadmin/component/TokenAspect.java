package club.mrdaisite.torder.torderadmin.component;

import club.mrdaisite.torder.torderadmin.util.JwtTokenUtil;
import club.mrdaisite.torder.torderadmin.util.LoggerUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * TokenAspect
 *
 * @author dai
 * @date 2019/04/15
 */
@Component
@Aspect
@Order(3)
public class TokenAspect {
    @Pointcut("execution(public * club.mrdaisite.torder.torderadmin.controller.*.*(..))")
    public void token() {
    }

    /**
     * 获取Authorization中的token
     */
    @Before("token()")
    public void doBefore() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authorization = servletRequestAttributes.getRequest().getHeader("Authorization");
        if (authorization != null) {
            JwtTokenUtil.token = authorization.replaceAll("^Bearer\\s+", "");
        }
    }
}
