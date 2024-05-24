package english_ai_tutor.writing_service.service;

import english_ai_tutor.writing_service.dto.SaplingRequest;
import english_ai_tutor.writing_service.dto.SaplingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SaplingAiClient {
    private final WebClient webClient;

    @Autowired
    public SaplingAiClient(@Qualifier("SaplingAIWebClient")WebClient webClient) {
        this.webClient = webClient;
    }

    public void edits(){
        SaplingRequest request = SaplingRequest.builder()
                .key("3AF51X1629SPJYYS1MDYBCQKNSO7OWRS")
                .text("She doesn't like to watching horor movies because they makes her feel very scared and she can't sleep good at night.")
                .session_id("test_session").build();

        SaplingResponse response = webClient.post()
                .uri("/api/v1/edits")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SaplingResponse.class)
                .block();


        response.getEdits().stream().forEach(edit -> System.out.println(edit.toString()));
    }
}
