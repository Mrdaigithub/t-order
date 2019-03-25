package club.mrdaisite.torder.torderadmin.component;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingPathVariableException;
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
    public ResponseEntity badCredentialsExceptionHandler(HttpServletRequest request, Exception exception) {
        return new CommonResult().unauthorized("用户名或密码错误");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity duplicateKeyExceptionHandler(HttpServletRequest request, Exception exception) {
        return new CommonResult().badRequest("参数指定的数据已存在");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity dataIntegrityViolationExceptionHandler(HttpServletRequest request, Exception exception) {
        return new CommonResult().internalServerError("插入到数据库的数据不完整");
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity missingPathVariableExceptionHandler(HttpServletRequest request, Exception exception) {
        return new CommonResult().internalServerError("路径参数不存在");
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity badSqlGrammarExceptionHandler(HttpServletRequest request, Exception exception) {
        return new CommonResult().internalServerError("错误的sql语法");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity nullPointerExceptionHandler(HttpServletRequest request, Exception exception) {
        return new CommonResult().notFound("空指针错误");
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity customExceptionHandler(HttpServletRequest request, Exception exception) {
        return new CommonResult().internalServerError(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(HttpServletRequest request, Exception exception) {
        return new CommonResult().internalServerError("未知错误");
    }
}
