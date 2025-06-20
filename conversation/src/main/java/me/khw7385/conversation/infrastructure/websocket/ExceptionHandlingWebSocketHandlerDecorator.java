package me.khw7385.conversation.infrastructure.websocket;

import lombok.extern.slf4j.Slf4j;
import me.khw7385.conversation.core.exception.ApplicationException;
import me.khw7385.conversation.infrastructure.websocket.client.ClientWebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

@Slf4j
public class ExceptionHandlingWebSocketHandlerDecorator extends WebSocketHandlerDecorator {
    public ExceptionHandlingWebSocketHandlerDecorator(ClientWebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            super.afterConnectionEstablished(session);
        }catch(ApplicationException e){
            log.error(e.getMessage());
            session.close();
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        try{
            super.handleMessage(session, message);
        }catch(ApplicationException e){
            log.error(e.getMessage());
            session.close();
        }
    }
}

