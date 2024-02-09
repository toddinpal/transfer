package oracle.examples.cloudbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;


@RestController
@RequestMapping("/")
public class TransferService {

    private static final Logger log = Logger.getLogger(TransferService.class.getSimpleName());
    public static final String TRANSFER_ID = "TRANSFER_ID";
    private static URI withdrawUri;
    private static URI depositUri;

    @Autowired
    private RestTemplate restTemplate;

    static {
        try {
            log.info("accountWithdrawUrl:" + ApplicationConfig.accountWithdrawUrl);
            withdrawUri = new URI(ApplicationConfig.accountWithdrawUrl);
            depositUri = new URI(ApplicationConfig.accountDepositUrl);
        } catch (URISyntaxException ex) {
            throw new IllegalStateException("Failed to initialize " + TransferService.class.getName(), ex);
        }
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseEntity<?> transfer(@RequestParam("fromAccount") long fromAccount,
            @RequestParam("toAccount") long toAccount,
            @RequestParam("amount") long amount) throws Exception {
        log.info("Started to transfer");
        String returnString = "";
        returnString += "\n    " + withdraw(fromAccount, amount);
        returnString += "\n    " + deposit(toAccount, amount);
        log.info(returnString);
        return ResponseEntity.ok("transfer status:" + returnString);
    }

    private String withdraw(long accountId, long amount) {
        log.info("withdraw accountId = " + accountId + ", amount = " + amount);
        URI accountUri = getTarget(withdrawUri)
                .queryParam("accountId", accountId)
                .queryParam("amount", amount)
                .build()
                .toUri();
        String withdrawOutcome = restTemplate.postForEntity(accountUri, null, String.class).getBody();
        log.info("WithdrawOutcome = " + withdrawOutcome);
        return withdrawOutcome;
    }

    private String deposit(long accountId, long amount) {
        log.info("deposit accountId = " + accountId + ", amount = " + amount);
        URI accountUri = getTarget(depositUri)
                .queryParam("accountId", accountId)
                .queryParam("amount", amount)
                .build()
                .toUri();
        String depositOutcome = restTemplate.postForEntity(accountUri, null, String.class).getBody();
        log.info("DepositOutcome = " + depositOutcome);
        return depositOutcome;
    }

    private UriComponentsBuilder getTarget(URI serviceUri) {
        return UriComponentsBuilder.fromUri(serviceUri);
    }

}
