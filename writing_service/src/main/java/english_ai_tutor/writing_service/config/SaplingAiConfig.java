package english_ai_tutor.writing_service.config;

import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SaplingAiConfig{
    @Value("${sapling.api.key}")
    private String SAPLING_API_KEY;

    @Bean(name="SaplingAIWebClient")
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("https://api.sapling.ai")
                .build();
    }
}
