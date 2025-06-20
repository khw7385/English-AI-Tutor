package me.khw7385.conversation.infrastructure.event;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.ConversationFacade;
import me.khw7385.conversation.application.port.inbound.AudioStreamingUseCase;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.infrastructure.event.dto.ClientAudioChunkReceivedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketClosedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketConnectedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static me.khw7385.conversation.infrastructure.websocket.openai.dto.ServerToAiRealtimeMessage.createAudioAppendMessage;
import static me.khw7385.conversation.infrastructure.websocket.openai.dto.ServerToAiRealtimeMessage.createSessionUpdateMessage;

@Component
@RequiredArgsConstructor
public class ClientWebSocketEventHandler {
    private final AudioStreamingUseCase audioStreamingUseCase;
    private final ConversationFacade conversationFacade;

    @EventListener
    public void handle(ClientWebSocketConnectedEvent event){
        MessageChannel channel = event.channel();
        audioStreamingUseCase.connect(event.webSocketId(), channel);

        String prompt = conversationFacade.findPrompt(event.themeId());
        audioStreamingUseCase.forward(event.webSocketId(), createSessionUpdateMessage(prompt));
    }

    @EventListener
    public void handle(ClientAudioChunkReceivedEvent event){
        audioStreamingUseCase.forward(event.webSocketId(), createAudioAppendMessage(event.audio()));
    }

    @EventListener
    public void handle(ClientWebSocketClosedEvent event) {
        audioStreamingUseCase.cleanUp(event.webSocketId());
    }
}
