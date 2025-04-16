package pl.piomin.services.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import pl.piomin.services.model.Account;
import pl.piomin.services.repository.AccountRepository;

import java.util.List;

@Service
public class AccountTools {

    private AccountRepository accountRepository;

    public AccountTools(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Tool(description = "根据人员 ID 查找所有帐户")
    public List<Account> getAccountsByPersonId(@ToolParam(description = "人员 ID") Long personId) {
        return accountRepository.findByPersonId(personId);
    }
}
