package me.khw7385.conversation.infrastructure;

import me.khw7385.conversation.config.WebSocketClientConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        WebSocketClientConfig.class, RealtimeWebSocketHandler.class, OpenAiRealtimeApi.class,
})
class OpenAiRealtimeApiTest {
    @Value("${openai.api-key}")
    private String apiKey;
    @Autowired
    private OpenAiRealtimeApi realtimeApi;

    @Test
    @DisplayName("Realtime Api 와 웹 소켓 연결 - 성공")
    public void openWebSocketSession_success(){

        WebSocketSession webSocketSession = realtimeApi.openWebSocketSession(apiKey);

        assertTrue(webSocketSession.isOpen());
    }
}