# demo · Spring Boot 学习项目

循序渐进的练习项目，每步一个可验收的小目标：
CRUD → 统一返回 → 参数校验 → 密码哈希 → 全局异常 → 接口文档 → 业务异常重构 → Spring AI 接入 → RAG 检索问答。

## 版本速查

> 只放版本号，细节（配置项、坑、环境）一律见 ima 知识库「配置信息」文件夹，避免两处维护不同步。

| 组件 | 版本 |
| --- | --- |
| JDK | 21.0.6 |
| Maven | 3.9.11 |
| Spring Boot | 3.5.16 |
| Spring AI | 1.1.8 |
| MyBatis-Plus | 3.5.12 |
| MySQL | 8.0（端口 3307，库 demo_db） |
| springdoc / Knife4j | 2.8.15 / 4.5.0 |
| MySQL Connector/J | 9.7.0 |

## 跑起来

需要三个环境变量（代码里不写明文）：`DB_PASSWORD`、`DEEPSEEK_API_KEY`、`DASHSCOPE_API_KEY`。

命令行启动必须显式指定端口 —— 本机有 `SERVER__PORT` 干扰：

```
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
```

## 接口

| 路径 | 说明 |
| --- | --- |
| `/user/*` | 用户 CRUD |
| `/ai/chat?q=` | 调 DeepSeek 返回文本 |
| `/rag/search?q=&topK=` | 向量检索，返回命中块（source / score / chunkIndex / preview / content） |
| `/rag/ask?q=&topK=` | 检索 + 拼 prompt 问答 |
| `/doc.html` | Knife4j 接口文档 |

## 关于 src/main/resources/docs

RAG 的语料目录。3 篇学习笔记被 `.gitignore` 里的 `学习笔记-*.md` 拦在仓库外（公开仓库，不放私人笔记）。

所以 clone 下来后该目录只剩 `docs/README.md`，`/rag/ask` 一定答不出东西 —— **这不是代码坏了**，说明见 `src/main/resources/docs/README.md`。
