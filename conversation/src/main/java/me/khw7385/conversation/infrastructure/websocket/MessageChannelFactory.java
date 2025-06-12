package me.khw7385.conversation.infrastructure.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@RequiredArgsConstructor
public class MessageChannelFactory {
    private final ObjectMapper objectMapper;

    public MessageChannel create(WebSocketSession session){
        return new WebSocketMessageChannel(session, objectMapper);
    }
}
