package com.example.demo.service;

import com.example.demo.dto.RagHit;
import org.springframework.ai.document.Document;


import java.util.List;

public interface RagService {
    List<RagHit> search(String q, int topK);
    String ask(String q,int topK);
}
