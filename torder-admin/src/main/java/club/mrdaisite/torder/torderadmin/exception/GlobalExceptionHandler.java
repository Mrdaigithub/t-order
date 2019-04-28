package club.mrdaisite.torder.torderadmin.exception;

import club.mrdaisite.torder.torderadmin.dto.CommonResult;
import club.mrdaisite.torder.torderadmin.util.ErrorCodeUtils;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        return new CommonResult().unauthorized(new ErrorCodeUtils(4010000).getEMessage());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity badCredentialsExceptionHandler(HttpServletRequest request, DisabledException exception) {
        return new CommonResult().forbidden(new ErrorCodeUtils(4030002).getEMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity duplicateKeyExceptionHandler(HttpServletRequest request, DuplicateKeyException exception) {
        return new CommonResult().badRequest(new ErrorCodeUtils(4000000).getEMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity dataIntegrityViolationExceptionHandler(HttpServletRequest request, DataIntegrityViolationException exception) {
        return new CommonResult().internalServerError(new ErrorCodeUtils(5000005).getEMessage());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity missingPathVariableExceptionHandler(HttpServletRequest request, MissingPathVariableException exception) {
        return new CommonResult().badRequest(new ErrorCodeUtils(4000003).getEMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity methodArgumentTypeMismatchExceptionHandler(HttpServletRequest request, MethodArgumentTypeMismatchException exception) {
        return new CommonResult().badRequest(new ErrorCodeUtils(4000004).getEMessage());
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity badSqlGrammarExceptionHandler(HttpServletRequest request, BadSqlGrammarException exception) {
        return new CommonResult().internalServerError(new ErrorCodeUtils(5000001).getEMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity nullPointerExceptionHandler(HttpServletRequest request, NullPointerException exception) {
        return new CommonResult().internalServerError(new ErrorCodeUtils(5000002).getEMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity accessDeniedExceptionHandler(HttpServletRequest request, AccessDeniedException exception) {
        return new CommonResult().forbidden(new ErrorCodeUtils(4030001).getEMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException exception) {
        return new CommonResult().methodNotAllowed(new ErrorCodeUtils(4050000).getEMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableExceptionHandler(HttpServletRequest request, HttpMessageNotReadableException exception) {
        return new CommonResult().badRequest(new ErrorCodeUtils(4000001).getEMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity httpMediaTypeNotSupportedExceptionHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException exception) {
        return new CommonResult().badRequest(new ErrorCodeUtils(4000002).getEMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity arithmeticExceptionHandler(HttpServletRequest request, ArithmeticException exception) {
        return new CommonResult().internalServerError(new ErrorCodeUtils(5000003).getEMessage());
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity arrayIndexOutOfBoundsExceptionHandler(HttpServletRequest request, ArrayIndexOutOfBoundsException exception) {
        return new CommonResult().internalServerError(new ErrorCodeUtils(5000004).getEMessage());
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity customBadRequestException(CustomBadRequestException exception) {
        return new CommonResult().badRequest(exception.getMessage());
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity customUnauthorizedException(CustomUnauthorizedException exception) {
        return new CommonResult().unauthorized(exception.getMessage());
    }

    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseEntity customForbiddenException(CustomForbiddenException exception) {
        return new CommonResult().forbidden(exception.getMessage());
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity customNotFoundException(CustomNotFoundException exception) {
        return new CommonResult().notFound(exception.getMessage());
    }

    @ExceptionHandler(CustomInternalException.class)
    public ResponseEntity customExceptionHandler(CustomInternalException exception) {
        return new CommonResult().internalServerError(exception.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity exceptionHandler(HttpServletRequest request, Exception exception) {
//        return new CommonResult().internalServerError(new ErrorCodeUtils(5000000).getEMessage());
//    }
}
