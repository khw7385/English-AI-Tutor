package me.khw7385.conversation.infrastructure.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RealtimeSessionCreateResponse(
        String id,
        String model,
        List<String> modalities,
        @JsonProperty("client_secret") ClientSecret clientSecret
){
    public record ClientSecret(
            String value,
            @JsonProperty("expires_at") Integer expiresAt){
    }
}
