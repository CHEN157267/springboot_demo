package com.example.demo.service.impl;

import com.example.demo.common.Result;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.AiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService{
    private final ChatClient chatClient;
    public AiServiceImpl(ChatClient.Builder builder) {   // ← 注入 Builder
        this.chatClient = builder.build();               // ← 造出 ChatClient
    }

    @Override
    public Result<String> chat(String q) {
        // 验证对话内容是否为空，其中isBlank()方法用于判断字符串是否为空白或者纯空格
        if (q == null || q.isBlank()) {
            throw new BusinessException("对话内容不能为空", 400);
        }
        q = q.trim();
        String answer = chatClient.prompt().user(q).call().content();
        return Result.success(answer) ;
    }
}
