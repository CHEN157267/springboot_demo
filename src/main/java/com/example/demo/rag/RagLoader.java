package com.example.demo.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RagLoader implements CommandLineRunner {

    private final SimpleVectorStore vectorStore;
    public RagLoader(SimpleVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Value("classpath*:docs/*.md")      // ③ 通配符注入：把 docs 下所有 md 一次拿进来
    private Resource[] docs;
    private int seq;

    @Override
    public void run(String... args) throws Exception {
        List<Document> allChunks = new ArrayList<>();
        seq = 0;
        // 下面五步
        for (Resource r : docs) {
            int before = allChunks.size();
            // 读取文件内容
            String content = new String(r.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            //一次切块
            String[] contentFirstHeader = content.split("(?m)^(?=## )");
            for (String sec : contentFirstHeader) {
        if (sec.isBlank())
            continue;
        //二次切块
        String[] tempArr = sec.split("(?m)^(?=### )");
        String parentTitle = sec.lines().findFirst().orElse("");
        addChunk(tempArr, allChunks, r,parentTitle);
    }
            System.out.println(r.getFilename() + " → " + (allChunks.size() - before) + " 块");
        }
        vectorStore.add(allChunks);
    }
//处理文档
    private void addChunk(String[] subs, List<Document> allChunks, Resource r, String parentTitle) {

        String source = r.getFilename();
        //A
        if (subs.length == 1) {
            if (subs[0].length() < 1200) {
                allChunks.add(new Document(subs[0], Map.of("source", source, "chunk_index", seq++, "section", parentTitle)));
                return;
            }
            isOverCharLimit(allChunks, new Document(subs[0], Map.of("source", source, "chunk_index", seq++, "section", parentTitle)));
            return;
        }
        //B
        if (subs[0].length() > 60) {
            allChunks.add(new Document(subs[0], Map.of("source", source, "chunk_index", seq++, "section", parentTitle)));
        }

        //C
        for (int i = 1; i < subs.length; i++) {
            if (subs[i].isBlank())
                continue;
            subs[i] = parentTitle + "\n" + subs[i];
            Document doc = new Document(subs[i], Map.of("source", source, "chunk_index", seq++, "section", parentTitle));
            if (subs[i].length() > 1200){
                isOverCharLimit(allChunks, doc);
                continue;
            }
            allChunks.add(doc);
        }
    }
    //判断处理超字数
    private  void isOverCharLimit(List<Document> allChunks, Document doc) {

            allChunks.addAll(TokenTextSplitter.builder()
                    .withPunctuationMarks(java.util.List.of('。', '！', '？', '；', '\n'))
                    .withChunkSize(300)
                    .build()
                    .split(doc));
        }
    }

