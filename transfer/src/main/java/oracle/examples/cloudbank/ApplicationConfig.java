package oracle.examples.cloudbank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Configuration
public class ApplicationConfig {

    private static final Logger log = Logger.getLogger(ApplicationConfig.class.getSimpleName());

    static String accountWithdrawUrl;
    static String accountDepositUrl;

    public ApplicationConfig(
            @Value("${account.withdraw.url}") String accountWithdrawUrl,
            @Value("${account.deposit.url}") String accountDepositUrl) {
        log.info("In ApplicationConfig constructor");
        ApplicationConfig.accountWithdrawUrl = accountWithdrawUrl;
        ApplicationConfig.accountDepositUrl = accountDepositUrl;
    }

    @Bean
    public RestTemplate restTemplateBean() {
        return new RestTemplate();
    }
}
