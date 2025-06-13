package me.khw7385.conversation.infrastructure.event.dto;

public record ClientAudioChunkReceivedEvent(
        String webSocketId,
        String audio
) {
}
