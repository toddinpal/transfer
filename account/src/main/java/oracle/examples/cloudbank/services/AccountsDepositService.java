package oracle.examples.cloudbank.services;
import oracle.examples.cloudbank.model.Account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@RestController
@RequestMapping("/deposit")
public class AccountsDepositService {
    private static final Logger log = Logger.getLogger(AccountsDepositService.class.getName());

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public ResponseEntity<?> deposit(@RequestParam("accountId") long accountId,
                                     @RequestParam("amount") long depositAmount) {
        log.info("...deposit " + depositAmount + " in account:" + accountId);
        Account account = AccountTransferDAO.instance().getAccountForAccountId(accountId);
        if (account==null) {
            log.info("deposit failed: account does not exist");
            return ResponseEntity.ok("deposit failed: account does not exist");
        }

        account.setAccountBalance(account.getAccountBalance() + depositAmount);
        AccountTransferDAO.instance().saveAccount(account);
        return ResponseEntity.ok("deposit succeeded");
    }

    
}
