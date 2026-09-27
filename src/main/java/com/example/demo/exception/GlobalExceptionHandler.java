package com.example.demo.exception;
import com.example.demo.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.warn("参数错误",e);
        return Result.error(400,e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e){
        log.error("系统繁忙，请稍后重试",e);
        return Result.error("系统繁忙，请稍后重试");
    }


}
