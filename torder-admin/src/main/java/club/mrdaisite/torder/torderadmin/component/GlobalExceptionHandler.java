package club.mrdaisite.torder.torderadmin.config;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 *
 * @author dai
 * @date 2019/03/22
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public CommonResult exceptionHandler(HttpServletRequest request, Exception exception){
        return new CommonResult().failed(exception.getMessage());
    }
}
