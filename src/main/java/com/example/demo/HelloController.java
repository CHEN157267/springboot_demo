package com.example.demo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "你好，控制器", description = "测试控制器")
public class HelloController {
    @GetMapping("/hello")
    @Operation(summary = "你好，get")
    public String hello(){
        return "Hello world!";
    }
    @PostMapping("/hello")
    @Operation(summary = "你好，post")
    public String helloPost(){
        return "PostMapping is running!";
    }
}
