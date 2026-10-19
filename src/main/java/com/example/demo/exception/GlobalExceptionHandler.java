package com.example.demo.exception;
import com.example.demo.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.warn("参数错误",e);
        return Result.error(400,e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e){
        log.warn("业务异常",e);
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(NonTransientAiException.class)
    public Result<Void> handleNoTransientAiException(NonTransientAiException e){
        log.error("AI 服务暂时不可用，请联系管理员",e);
        return Result.error(503, "AI 服务暂时不可用，请联系管理员");
    }

    @ExceptionHandler(ResourceAccessException.class)
    public Result<Void> handleResourceAccessException(ResourceAccessException e){
        log.warn("AI 服务响应超时，请稍后再试",e);
        return Result.error(503, "AI 服务响应超时，请稍后再试");
    }

    // 处理缺少必需参数的异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Void> handleMissingParam(MissingServletRequestParameterException e){
        log.warn("缺少必需参数: {}", e.getParameterName());      // 只记一行，不打堆栈
        return Result.error(400, "缺少必需参数：" + e.getParameterName());
    }

    // 处理资源未找到的异常

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<Void> handleNoResourceFoundException(NoResourceFoundException e){
//    log.warn("资源未找到", e) 会把整条堆栈打出来。
//        favicon 是每个浏览器都会自动请求的、注定 404 的常态请求。
//        日志会被它刷屏。建议只记一行
//        （log.warn("资源未找到: {}", e.getMessage())），
//        不传整个异常。

        log.warn("资源未找到: {}", e.getMessage());
        return Result.error(404,e.getMessage());
    }

    //兜底异常
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e){
        log.error("系统繁忙，请稍后重试",e);
        return Result.error("系统繁忙，请稍后重试");
    }

}
