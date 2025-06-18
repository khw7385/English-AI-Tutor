package me.khw7385.conversation.infrastructure.event;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.ConversationFacade;
import me.khw7385.conversation.application.port.inbound.AudioStreamingUseCase;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.core.exception.MessageChannelConnectionException;
import me.khw7385.conversation.core.exception.MessageChannelNotFoundException;
import me.khw7385.conversation.core.exception.MessageTransferException;
import me.khw7385.conversation.infrastructure.event.dto.ClientAudioChunkReceivedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketClosedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketConnectedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static me.khw7385.conversation.infrastructure.websocket.openai.ServerToAiRealtimeMessage.*;

@Component
@RequiredArgsConstructor
public class ClientWebSocketEventHandler {
    private final AudioStreamingUseCase audioStreamingUseCase;
    private final ConversationFacade conversationFacade;

    @EventListener
    public void handle(ClientWebSocketConnectedEvent event){
        MessageChannel channel = event.channel();
        try{
            audioStreamingUseCase.connect(event.webSocketId(), channel);

            String prompt = conversationFacade.findPrompt(event.themeId());
            audioStreamingUseCase.forward(event.webSocketId(), createSessionUpdateMessage(prompt));
        }catch(MessageChannelConnectionException e){
            channel.close();
        }
    }

    @EventListener
    public void handle(ClientAudioChunkReceivedEvent event){
        try {
            audioStreamingUseCase.forward(event.webSocketId(), createAudioAppendMessage(event.audio()));
        }catch(MessageChannelNotFoundException | MessageTransferException e){
            audioStreamingUseCase.releaseChannel(event.webSocketId());
        }
    }

    @EventListener
    public void handle(ClientWebSocketClosedEvent event) {
        audioStreamingUseCase.releasePairChannel(event.webSocketId());
    }
}
