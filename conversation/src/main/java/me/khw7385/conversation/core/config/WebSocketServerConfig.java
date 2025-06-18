package me.khw7385.conversation.core.config;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.core.interceptor.WebSocketInterceptor;
import me.khw7385.conversation.infrastructure.websocket.client.ClientWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketServerConfig implements WebSocketConfigurer {
    private final ClientWebSocketHandler clientWebSocketHandler;
    private final WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(clientWebSocketHandler, "/conversation/streaming")
                .addInterceptors(webSocketInterceptor);
    }
}
