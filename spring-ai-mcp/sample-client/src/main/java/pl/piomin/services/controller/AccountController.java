package pl.piomin.services.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final static Logger LOG = LoggerFactory.getLogger(AccountController.class);
    private final ChatClient chatClient;

    public AccountController(@Qualifier("doubaoChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/count-by-person-id/{personId}")
    String countByPersonId(@PathVariable String personId) {
        PromptTemplate pt = new PromptTemplate("""
                人员 ID 为 {personId} 的人有多少个帐户？
                """);
        Prompt p = pt.create(Map.of("personId", personId));
        return this.chatClient.prompt(p)
                .call()
                .content();
    }

    @GetMapping("/balance-by-person-id/{personId}")
    String balanceByPersonId(@PathVariable String personId) {
        PromptTemplate pt = new PromptTemplate("""
                人员 ID 为 {personId} 的人有多少个帐户？
                返回个人姓名、国籍和其账户的总余额。
                """);
        Prompt p = pt.create(Map.of("personId", personId));
        return this.chatClient.prompt(p)
                .call()
                .content();
    }

}
