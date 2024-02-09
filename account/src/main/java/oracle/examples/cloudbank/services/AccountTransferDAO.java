package oracle.examples.cloudbank.services;

import oracle.examples.cloudbank.model.Account;
import oracle.examples.cloudbank.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class AccountTransferDAO {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static AccountTransferDAO singleton;
    final AccountRepository accountRepository;
    public AccountTransferDAO(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        singleton = this;
    }

    public static AccountTransferDAO instance() {
        return singleton;
    }


    public void saveAccount(Account account) {
        log.info("saveAccount account" + account.getAccountId() + " account" + account.getAccountBalance());
        accountRepository.save(account);
    }

     Account getAccountForAccountId(long accountId)  {
         Account account = accountRepository.findByAccountId(accountId);
         if (account == null) return null;
         return account;
    }

}
