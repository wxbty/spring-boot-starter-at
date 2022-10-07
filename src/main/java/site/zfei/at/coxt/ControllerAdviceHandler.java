package site.zfei.at.coxt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.zfei.at.trace.WebLogBean;

import java.util.List;

/**
 * @author fgh
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleException(HttpMessageNotReadableException ex) {
        return handle(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError objectError : errors) {
                FieldError fieldError = (FieldError) objectError;
                log.error("Data check failure : object: {},field: {},errorMessage: {}",
                        fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                errorMsg.append(objectError.getDefaultMessage());
                errorMsg.append(",");
            }
            errorMsg = new StringBuilder(errorMsg.substring(0, errorMsg.length() - 1));
            return new Result(Sts.BAD_REQUEST.value(), errorMsg.toString());
        }
        return new Result(Sts.UNKNOWN.value(), exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Result handleException(HttpMessageNotWritableException ex) {
        return handle(ex, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Result handleException(HttpMediaTypeNotAcceptableException ex) {
        return handle(ex, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Result handleException(HttpMediaTypeNotSupportedException ex) {
        return handle(ex, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result handleException(HttpRequestMethodNotSupportedException ex) {
        return handle(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(StsException.class)
    public Result handleException(StsException e) {
        return new Result(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Throwable ex) {
        log.error(ex.getMessage(), ex);
        WebLogBean logBean = WebLogBean.getLogBean();
        logBean.setCode(500);
        if (ex.getCause() != null) {
            logBean.setError(ex.getCause().getMessage());
        } else {
            logBean.setError(ex.getMessage());
        }
        return new Result(500, ex.getMessage());
    }

    private Result handle(Throwable ex, HttpStatus status) {
        WebLogBean logBean = WebLogBean.getLogBean();
        logBean.setCode(status.value());
        logBean.setError(ex.getMessage());
        return new Result(status.value(), status.getReasonPhrase());
    }
}
