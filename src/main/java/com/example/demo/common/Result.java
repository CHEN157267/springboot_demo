package com.example.demo.common;

import io.swagger.v3.oas.annotations.media.Schema;

public class Result<T> {
    @Schema(description = "数据")
    private T data;
    @Schema(description = "状态码")
    private int code;
    @Schema(description = "提示信息")
    private String message;
    private Result(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }
    public static <T> Result<T> success(T data){
        return new Result<>(data, 200, "success");
    }
    public static <T> Result<T> error(String message){
        return new Result<>(null, 500, message);
    }
    public static <T> Result<T> error(int code, String message){
        return new Result<>(null, code, message);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
