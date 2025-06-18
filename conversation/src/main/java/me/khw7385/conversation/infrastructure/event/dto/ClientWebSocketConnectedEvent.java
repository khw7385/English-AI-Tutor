package me.khw7385.conversation.infrastructure.event.dto;

import me.khw7385.conversation.application.port.outbound.MessageChannel;

public record ClientWebSocketConnectedEvent(
        String webSocketId,
        MessageChannel channel,
        Long themeId
) {
}
