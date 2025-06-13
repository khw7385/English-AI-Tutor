package me.khw7385.conversation.infrastructure.event;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.port.inbound.AudioStreamingUseCase;
import me.khw7385.conversation.infrastructure.event.dto.ClientAudioChunkReceivedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketClosedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketConnectedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientWebSocketEventHandler {
    private final AudioStreamingUseCase audioStreamingUseCase;

    @EventListener
    public void handle(ClientWebSocketConnectedEvent event){
        audioStreamingUseCase.connect(event.webSocketId(), event.channel());
    }

    @EventListener
    public void handle(ClientAudioChunkReceivedEvent event){
        audioStreamingUseCase.forward(event.webSocketId(), event.audio());
    }

    @EventListener
    public void handle(ClientWebSocketClosedEvent event) {
        audioStreamingUseCase.close(event.webSocketId());
    }
}
