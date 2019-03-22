package club.mrdaisite.torder.torderadmin.component;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 *
 * @author dai
 * @date 2019/03/22
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @ExceptionHandler(BadCredentialsException.class)
    public CommonResult badCredentialsExceptionHandler(HttpServletRequest request, Exception exception) {
        LOGGER.error(exception.getMessage());
        return new CommonResult().failed("");
    }

    @ExceptionHandler(Exception.class)
    public CommonResult exceptionHandler(HttpServletRequest request, Exception exception) {
        LOGGER.error(exception.getClass().getName() + ": " + exception.getMessage());
        return new CommonResult().failed("未知错误");
    }
}
