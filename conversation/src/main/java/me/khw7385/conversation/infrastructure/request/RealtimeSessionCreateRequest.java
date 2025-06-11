package me.khw7385.conversation.infrastructure.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RealtimeSessionCreateRequest(
        String model,
        List<String> modalities,
        String instruction,
        @JsonProperty("client_secret") ClientSecret clientSecret

) {
    public static RealtimeSessionCreateRequest of(String model){
        return RealtimeSessionCreateRequest.builder()
                .model(model)
                .modalities(List.of("audio", "text"))
//                .clientSecret(new ClientSecret(new ClientSecret.ExpiresAt("createdAt", 60)))
                .build();
    }

    public record ClientSecret(
        @JsonProperty("expires_at") ExpiresAt expiresAt
    ){
        public record ExpiresAt(
            String anchor,
            Integer seconds
        ){}
    }
}
