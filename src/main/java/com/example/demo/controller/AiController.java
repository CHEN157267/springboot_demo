package com.example.demo.controller;
import com.example.demo.common.Result;
import com.example.demo.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {
    private final AiService aiService;
    public AiController(AiService aiService){
        this.aiService = aiService;
    }

    //deepseek 处理信息的接口
    @GetMapping("/chat")
    @Operation(summary = "AI聊天", description = "与deepseek对话")
    public Result<String> chat(@RequestParam String q){
        return aiService.chat(q) ;
    }
}
