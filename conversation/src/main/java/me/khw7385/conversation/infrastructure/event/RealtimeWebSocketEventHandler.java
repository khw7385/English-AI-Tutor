package me.khw7385.conversation.infrastructure.event;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.port.inbound.AudioStreamingUseCase;
import me.khw7385.conversation.core.exception.MessageChannelNotFoundException;
import me.khw7385.conversation.core.exception.MessageTransferException;
import me.khw7385.conversation.infrastructure.event.dto.RealtimeAudioChunkReceivedEvent;
import me.khw7385.conversation.infrastructure.event.dto.RealtimeWebSocketClosedEvent;
import me.khw7385.conversation.infrastructure.websocket.client.ServerToClientAudioMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealtimeWebSocketEventHandler {
    private final AudioStreamingUseCase audioStreamingUseCase;

    @EventListener
    public void handle(RealtimeAudioChunkReceivedEvent event){
        try {
            audioStreamingUseCase.forward(event.webSocketId(), new ServerToClientAudioMessage(event.audio()));
        }catch(MessageChannelNotFoundException | MessageTransferException e){
            audioStreamingUseCase.releaseChannel(event.webSocketId());
        }
    }

    @EventListener
    public void handle(RealtimeWebSocketClosedEvent event) {
        audioStreamingUseCase.releasePairChannel(event.webSocketId());
    }
}
