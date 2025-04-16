package pl.piomin.services;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.tools.AccountTools;

@SpringBootApplication
public class AccountMCPServer {

    public static void main(String[] args) {
        SpringApplication.run(AccountMCPServer.class, args);
    }

    @Bean
    public ToolCallbackProvider tools(AccountTools accountTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(accountTools)
                .build();
    }

}
