package me.khw7385.conversation.infrastructure.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.khw7385.conversation.infrastructure.enums.RealtimeEventType;

public record RealtimeEventResponse(
        @JsonProperty("eventId") String eventId,
        RealtimeEventType type,
        @JsonProperty("response_id") String responseId,
        @JsonProperty("item_id") String itemId,
        String delta
) {
}
