package me.khw7385.conversation.infrastructure.websocket.client;

import me.khw7385.conversation.application.port.outbound.Message;

public record ServerToClientAudioMessage(
        String audio
) implements Message {
}
