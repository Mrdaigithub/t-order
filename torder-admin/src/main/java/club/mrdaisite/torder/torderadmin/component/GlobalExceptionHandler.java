package club.mrdaisite.torder.torderadmin.component;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
    public ResponseEntity badCredentialsExceptionHandler(HttpServletRequest request, BadCredentialsException exception) {
        return new CommonResult().unauthorized("用户名或密码错误");
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity badCredentialsExceptionHandler(HttpServletRequest request, DisabledException exception) {
        return new CommonResult().forbidden("此用户未启用");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity duplicateKeyExceptionHandler(HttpServletRequest request, DuplicateKeyException exception) {
        return new CommonResult().badRequest("参数指定的数据已存在");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity dataIntegrityViolationExceptionHandler(HttpServletRequest request, DataIntegrityViolationException exception) {
        return new CommonResult().internalServerError("插入到数据库的数据不完整");
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity missingPathVariableExceptionHandler(HttpServletRequest request, MissingPathVariableException exception) {
        return new CommonResult().internalServerError("路径参数不存在");
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity badSqlGrammarExceptionHandler(HttpServletRequest request, BadSqlGrammarException exception) {
        return new CommonResult().internalServerError("错误的sql语法");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity nullPointerExceptionHandler(HttpServletRequest request, NullPointerException exception) {
        return new CommonResult().notFound("空指针错误");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity accessDeniedExceptionHandler(HttpServletRequest request, AccessDeniedException exception) {
        return new CommonResult().forbidden("不允许访问");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException exception) {
        return new CommonResult().methodNotAllowed("请求方法不支持");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableExceptionHandler(HttpServletRequest request, HttpMessageNotReadableException exception) {
        return new CommonResult().badRequest("必要的请求参数不存在");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity httpMediaTypeNotSupportedExceptionHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException exception) {
        return new CommonResult().badRequest("Http媒体类型不支持");
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity arithmeticExceptionHandler(HttpServletRequest request, ArithmeticException exception) {
        return new CommonResult().badRequest("算术异常");
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity arrayIndexOutOfBoundsExceptionHandler(HttpServletRequest request, ArrayIndexOutOfBoundsException exception) {
        return new CommonResult().internalServerError("数组索引超出界限");
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity customExceptionHandler(CustomException exception) {
        return new CommonResult().internalServerError(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(HttpServletRequest request, Exception exception) {
        return new CommonResult().internalServerError("未知错误");
    }
}
