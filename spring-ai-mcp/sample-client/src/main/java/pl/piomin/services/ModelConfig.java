package pl.piomin.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    /**
     * 多个模型的 ChatClient 共享同一个内存实例。
     * <p>
     * 默认情况下，Spring AI 的 ChatMemory 使用会话 ID（conversation ID）来区分不同的对话。
     * 如果没有显式指定会话 ID，Spring AI 会使用一个默认的会话 ID（"default"）。
     */
//    @Bean
//    public InMemoryChatMemory chatMemory() {
//        return new InMemoryChatMemory();
//    }
//    @Bean(name = "chatMemory")
//    public RedisMySQLChatMemory chatMemory(RedisMySQLChatMemory redisMySQLChatMemory) {
//        return redisMySQLChatMemory;
//    }

    // 豆包配置
    @Bean(name = "doubaoChatModel")
    public OpenAiChatModel doubaoChatModel() {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api")
                .apiKey(System.getenv("DOUBAO_API_KEY"))
                .build();
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("deepseek-v3-250324")
                .temperature(1.0)
                .maxTokens(2048)
                .build();
        return OpenAiChatModel.builder().openAiApi(openAiApi).defaultOptions(options).build();
    }

    @Bean(name = "doubaoChatClientBuilder")
    public ChatClient.Builder doubaoChatClientBuilder(@Qualifier("doubaoChatModel") OpenAiChatModel doubaoChatModel) {
        return ChatClient.builder(doubaoChatModel)
                .defaultSystem("你是一个优秀的 AI 智能助手，请提供准确清晰且有帮助的回答")
                // MessageChatMemoryAdvisor 可选
                .defaultAdvisors(new SimpleLoggerAdvisor());
    }

    @Bean(name = "doubaoChatClient")
    public ChatClient doubaoChatClient(@Qualifier("doubaoChatClientBuilder") ChatClient.Builder builder) {
        return builder.build();
    }

//    @Bean
//    public EmbeddingModel openAiEmbeddingModel() {
//        OpenAiApi openAiApi = OpenAiApi.builder()
//                .baseUrl("https://api.deepseek.com")
//                .apiKey(System.getenv("DEEPSEEK_API_KEY"))
//                .build();
//        return new OpenAiEmbeddingModel(openAiApi);
//    }
}
