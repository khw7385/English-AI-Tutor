package me.khw7385.conversation.infrastructure.event.dto;

public record ClientWebSocketClosedEvent(
        String webSocketId
) {
}
