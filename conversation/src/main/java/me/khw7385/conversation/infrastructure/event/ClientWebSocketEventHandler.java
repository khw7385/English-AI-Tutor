package me.khw7385.conversation.infrastructure.event;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.port.inbound.AudioStreamingUseCase;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.core.exception.MessageChannelConnectionException;
import me.khw7385.conversation.core.exception.MessageChannelNotFoundException;
import me.khw7385.conversation.core.exception.MessageTransferException;
import me.khw7385.conversation.infrastructure.enums.RealtimeEventType;
import me.khw7385.conversation.infrastructure.event.dto.ClientAudioChunkReceivedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketClosedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketConnectedEvent;
import me.khw7385.conversation.infrastructure.websocket.openai.ServerToAiRealtimeMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientWebSocketEventHandler {
    private final AudioStreamingUseCase audioStreamingUseCase;

    @EventListener
    public void handle(ClientWebSocketConnectedEvent event){
        MessageChannel channel = event.channel();
        try{
            audioStreamingUseCase.connect(event.webSocketId(), channel);
        }catch(MessageChannelConnectionException e){
            channel.close();
        }
    }

    @EventListener
    public void handle(ClientAudioChunkReceivedEvent event){
        try {
            audioStreamingUseCase.forward(event.webSocketId(),
                    ServerToAiRealtimeMessage.of(RealtimeEventType.CLIENT_INPUT_AUDIO_BUFFER_APPEND, event.audio()));
        }catch(MessageChannelNotFoundException | MessageTransferException e){
            audioStreamingUseCase.releaseChannel(event.webSocketId());
        }
    }

    @EventListener
    public void handle(ClientWebSocketClosedEvent event) {
        audioStreamingUseCase.close(event.webSocketId());
    }
}
