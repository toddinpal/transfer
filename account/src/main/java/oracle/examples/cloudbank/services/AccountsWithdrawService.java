package oracle.examples.cloudbank.services;

import oracle.examples.cloudbank.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@RestController
@RequestMapping("/withdraw")
public class AccountsWithdrawService {
    private static final Logger log = Logger.getLogger(AccountsWithdrawService.class.getName());

    /**
     * Reduce account balance by given amount if not overdrawn
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public ResponseEntity<?> withdraw(@RequestParam("accountId") long accountId,
                            @RequestParam("amount") long withdrawAmount)  {
        log.info("withdraw " + withdrawAmount + " in account:" + accountId );
        Account account = AccountTransferDAO.instance().getAccountForAccountId(accountId);
        if (account==null) {
            log.info("withdraw failed: account does not exist"); //could also do leave here
            return ResponseEntity.ok("withdraw failed: account does not exist");
        }
        if (account.getAccountBalance() < withdrawAmount) {
            log.info("withdraw failed: insufficient funds");
            return ResponseEntity.ok("withdraw failed: insufficient funds");
        }
        log.info("withdraw current balance:" + account.getAccountBalance() +
                " new balance:" + (account.getAccountBalance() - withdrawAmount));
        
        account.setAccountBalance(account.getAccountBalance() - withdrawAmount);
        AccountTransferDAO.instance().saveAccount(account);
        return ResponseEntity.ok("withdraw succeeded");
    }
}
