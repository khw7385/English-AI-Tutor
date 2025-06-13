package me.khw7385.conversation.infrastructure.event.dto;

public record RealtimeAudioChunkReceivedEvent(
        String webSocketId,
        String audio
) {
}
