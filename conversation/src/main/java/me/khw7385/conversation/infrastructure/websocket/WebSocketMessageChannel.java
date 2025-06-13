package me.khw7385.conversation.infrastructure.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.infrastructure.enums.RealtimeEventType;
import me.khw7385.conversation.infrastructure.request.RealtimeEventRequest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class WebSocketMessageChannel implements MessageChannel {
    private final WebSocketSession session;
    private final ObjectMapper objectMapper;

    @Override
    public String getId() {
        return session.getId();
    }

    @Override
    public void sendAudioMessage(String base64chunk) {
        try {
            String json = objectMapper.writeValueAsString(
                    RealtimeEventRequest.of(RealtimeEventType.CLIENT_INPUT_AUDIO_BUFFER_APPEND, base64chunk));
            session.sendMessage(new TextMessage(json));
        } catch (IOException e) {
            // 임시 처리
            throw new RuntimeException(e);
        }
    }

    public void close(){
        try {
            session.close();
        } catch (IOException e) {
            log.warn("WebSocket 세션 종 중 I/O 오류 발 (sessionId={}): {}", session.getId(), e.getMessage());
        }
    }
}
