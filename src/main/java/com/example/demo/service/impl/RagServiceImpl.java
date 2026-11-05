package com.example.demo.service.impl;

import com.example.demo.dto.RagHit;
import com.example.demo.service.RagService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

@Service
public class RagServiceImpl implements RagService {
    private final SimpleVectorStore vectorStore;
    private final ChatClient chatClient;
    public RagServiceImpl(SimpleVectorStore vectorStore,ChatClient.Builder builder) {
        this.vectorStore = vectorStore;
        this.chatClient = builder.build();
    }

    @Override
    public List<RagHit> search(String q, int topK) {
        List<RagHit> ragHits = new ArrayList<>();
        List<Document> result = vectorStore
                .similaritySearch(SearchRequest.builder()
                        .query(q).
                        topK(topK)
                        .build());
        for (Document doc : result){
            //暂时处理chunk_index可能不存在的情况
            Object ci = doc.getMetadata().get("chunk_index");
            int idx = (ci == null) ? 0 : Integer.parseInt(ci.toString());

            RagHit ragHit = new RagHit();
             ragHit.source(String.valueOf(doc.getMetadata().get("source")))
                    .score(doc.getScore())
                    .chunkIndex(idx)
                     .content(doc.getText())
                     .preview(doc.getText()
                             .replace("\n", " ")
                             .replaceAll("\\s+", " "));
                             ragHit.setPreview(ragHit.getPreview().substring(0, Math.min(ragHit.getPreview()
                                     .length(), 50)));
            ragHits.add(ragHit);
        }
        return ragHits;
    }
    @Override
    public String ask(String q, int topK){
        List<RagHit> ragHits = new ArrayList<>();
        double scoreThreshold = 0.5;
        for (RagHit ragHit : search(q, topK)){
            if (ragHit.getScore() >= scoreThreshold){
                ragHits.add(ragHit);
            }
        }
        if (ragHits.isEmpty())
            return "笔记里没有相关内容";

        StringBuilder sb = new StringBuilder();
        sb.append("ragHits:[" + '\n');
        for ( int i = 0; i < ragHits.size(); i++){
            RagHit ragHit = ragHits.get(i);
            sb.append("【资料" + (i+1) + "】来源：" +
                            ragHit.getSource() + "\n" +
                            ragHit.getContent() + "\n\n"
                    );
        }
        sb.append("]");
        sb.append("question:")
        .append(q);

        return chatClient.prompt()
                .system("你是一名知识库问答助手。只根据【资料】回答，不要用你自己的知识补充。" +
                        "资料里没有的信息，直接回答'笔记里没有相关内容'。" +
                        "资料之间如有冲突，以更具体的那条为准。")
                .user(sb.toString())
                .call()
                .content();
    }
}
