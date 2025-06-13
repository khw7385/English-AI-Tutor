package me.khw7385.conversation.infrastructure.websocket.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.khw7385.conversation.infrastructure.event.dto.ClientAudioChunkReceivedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketConnectedEvent;
import me.khw7385.conversation.infrastructure.event.dto.ClientWebSocketClosedEvent;
import me.khw7385.conversation.infrastructure.websocket.MessageChannelFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Slf4j
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
        ClientToServerAudioMessage request = objectMapper.readValue(message.getPayload(), ClientToServerAudioMessage.class);
        eventPublisher.publishEvent(new ClientAudioChunkReceivedEvent(session.getId(), request.audio()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("Client WebSocket 연결 종료. id={}", session.getId());
        eventPublisher.publishEvent(new ClientWebSocketClosedEvent(session.getId()));
    }
}
