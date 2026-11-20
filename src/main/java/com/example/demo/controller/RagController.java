package com.example.demo.controller;

import com.example.demo.common.Result;

import com.example.demo.dto.RagHit;
import com.example.demo.service.RagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/rag")
public class RagController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RagController.class);
    private final RagService ragService;
    public RagController(RagService ragService) {
        this.ragService = ragService;
    }
    @GetMapping("/search")
    public Result<List<RagHit>> searchTest(@RequestParam String q, @RequestParam(defaultValue = "5") int topK) {
        if (q == null || q.isBlank()) {
            log.warn("q 为空，已拒绝");
            return Result.error(400, "q 不能为空");
        }

        return Result.success(ragService.search(q, topK));
    }
    @GetMapping("/ask")
    public Result<String> answer(@RequestParam String q,@RequestParam(defaultValue = "5") int topK){
        if (q == null || q.isBlank()) {
            log.warn("q 为空，已拒绝");
            return Result.error(400, "q 不能为空");
        }

        return Result.success(ragService.ask(q, topK));
    }
}
