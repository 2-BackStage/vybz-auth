package back.vybz.auth_service.common.config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoolSmsConfig {

    @Value("${cools.api.key}")
    private String apiKey;

    @Value("${cools.api.secret}")
    private String apiSecret;

    @Bean
    public DefaultMessageService messageService() {
        return NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }
}
