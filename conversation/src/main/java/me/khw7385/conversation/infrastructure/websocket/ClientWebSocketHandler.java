package me.khw7385.conversation.infrastructure.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.infrastructure.event.dto.ClientAudioChunkReceivedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketConnectedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketClosedEvent;
import me.khw7385.conversation.infrastructure.request.AudioChunkRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
@RequiredArgsConstructor
public class ClientWebSocketHandler extends AbstractWebSocketHandler {
    private final ApplicationEventPublisher eventPublisher;
    private final MessageChannelFactory messageChannelFactory;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        eventPublisher.publishEvent(new ClientWebSocketConnectedEvent(session.getId(), messageChannelFactory.create(session)));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        AudioChunkRequest request = objectMapper.readValue(message.getPayload(), AudioChunkRequest.class);
        eventPublisher.publishEvent(new ClientAudioChunkReceivedEvent(session.getId(), request.audio()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        eventPublisher.publishEvent(new ClientWebSocketClosedEvent(session.getId()));
    }
}
