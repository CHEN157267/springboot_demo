package com.example.demo.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class RagHit {

    private String source;     // 来自哪篇
    private double score;      // 相似度
    private int chunkIndex;   // 第几块
    private String preview;   // 前 50 字
    @JsonIgnore
    private String content;   // 内容

    public RagHit(){}
    public RagHit source(String source){
        this.source = source;
        return this;
    }
    public RagHit score(double score){
        this.score = score;
        return this;
    }
    public RagHit chunkIndex(int chunkIndex){
        this.chunkIndex = chunkIndex;
        return this;
    }
    public RagHit preview(String preview){
        this.preview = preview;
        return this;
    }
    public RagHit content(String content){
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "RagHit{" +
                "source='" + source + '\n' +
                ", score=" + score +'\n' +
                ", chunkIndex=" + chunkIndex + '\n' +
                ", preview='" + preview + '\n' +
                ", content='" + content + '\n' +
                '}';
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(int chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public String getPreview() {
        return preview;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}
