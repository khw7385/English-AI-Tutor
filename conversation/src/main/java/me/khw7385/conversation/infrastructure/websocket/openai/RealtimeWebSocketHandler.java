package me.khw7385.conversation.infrastructure.websocket.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.khw7385.conversation.infrastructure.enums.RealtimeEventType;
import me.khw7385.conversation.infrastructure.event.dto.RealtimeAudioChunkReceivedEvent;
import me.khw7385.conversation.infrastructure.event.dto.RealtimeWebSocketClosedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class RealtimeWebSocketHandler extends AbstractWebSocketHandler {
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Realtime WebSocket 연걸 성공: session id = {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        AiToServerRealtimeMessage response = objectMapper.readValue(message.getPayload(), AiToServerRealtimeMessage.class);
        log.info("메시지 이벤트 타입: {}", response.type().getValue());

        if (response.type().equals(RealtimeEventType.RESPONSE_AUDIO_DELTA)) {
            eventPublisher.publishEvent(new RealtimeAudioChunkReceivedEvent(session.getId(), response.delta()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        eventPublisher.publishEvent(new RealtimeWebSocketClosedEvent(session.getId()));
        log.info("Realtime WebSocket 연결 종료: session id={}", session.getId());
    }
}
