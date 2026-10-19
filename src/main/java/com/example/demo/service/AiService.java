package com.example.demo.service;

import com.example.demo.common.Result;

public interface AiService {
    Result<String> chat(String q);
}
