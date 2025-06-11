package me.khw7385.conversation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${openai.api-key}")
    private String apiKey;

    private static final String OPENAI_BASE_URL = "https://api.openai.com/v1";

    @Bean
    public RestClient restClient(){
        return RestClient.builder()
                .baseUrl(OPENAI_BASE_URL)
                .defaultHeaders(headers -> {
                    headers.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", apiKey));
                    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }
}
