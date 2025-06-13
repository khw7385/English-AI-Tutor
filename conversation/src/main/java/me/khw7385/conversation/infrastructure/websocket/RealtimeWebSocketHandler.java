package me.khw7385.conversation.infrastructure.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.khw7385.conversation.infrastructure.response.RealtimeEventResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class RealtimeWebSocketHandler extends AbstractWebSocketHandler {
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        RealtimeEventResponse response = objectMapper.readValue(message.getPayload(), RealtimeEventResponse.class);
        log.info("{}", response.type().getValue());

    }
}
