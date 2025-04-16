package pl.piomin.services;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.tools.PersonTools;

import java.util.List;

@SpringBootApplication
public class PersonMCPServer {

    public static void main(String[] args) {
        SpringApplication.run(PersonMCPServer.class, args);
    }

    @Bean
    public ToolCallbackProvider tools(PersonTools personTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(personTools)
                .build();
    }

    @Bean
    public List<McpServerFeatures.SyncPromptSpecification> myPrompts() {
        var prompt = new McpSchema.Prompt("persons-by-nationality", "按国籍获取人员",
                List.of(new McpSchema.PromptArgument("nationality", "人员国籍", true)));

        var promptSpecification = new McpServerFeatures.SyncPromptSpecification(prompt, (exchange, getPromptRequest) -> {
            String argument = (String) getPromptRequest.arguments().get("nationality");
            var userMessage = new McpSchema.PromptMessage(McpSchema.Role.USER,
                    new McpSchema.TextContent("有多少人来自 " + argument + " 国家?"));
            return new McpSchema.GetPromptResult("按国籍统计人数", List.of(userMessage));
        });

        return List.of(promptSpecification);
    }
}
