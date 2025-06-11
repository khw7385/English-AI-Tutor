package me.khw7385.conversation.infrastructure;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.domain.RealtimeSession;
import me.khw7385.conversation.infrastructure.request.RealtimeSessionCreateRequest;
import me.khw7385.conversation.infrastructure.response.RealtimeSessionCreateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OpenAiSessionCreationApi {
    private static final String REALTIME_SESSION_PATH = "/realtime/sessions";

    @Value("${openai.realtime-model}")
    private String model;

    private final RestClient restClient;

    public RealtimeSession createSession(){
        RealtimeSessionCreateResponse response = restClient.post()
                .uri(REALTIME_SESSION_PATH)
                .body(RealtimeSessionCreateRequest.of(model))
                .retrieve()
                .body(RealtimeSessionCreateResponse.class);

        return map(Objects.requireNonNull(response));
    }

    private RealtimeSession map(RealtimeSessionCreateResponse response){
        RealtimeSessionCreateResponse.ClientSecret clientSecret = response.clientSecret();
        return RealtimeSession.of(clientSecret.value(), clientSecret.expiresAt());
    }
}
