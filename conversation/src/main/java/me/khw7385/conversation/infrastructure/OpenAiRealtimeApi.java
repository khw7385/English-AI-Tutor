package me.khw7385.conversation.infrastructure;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.application.port.outbound.RealtimeApi;
import me.khw7385.conversation.infrastructure.websocket.MessageChannelFactory;
import me.khw7385.conversation.infrastructure.websocket.openai.RealtimeWebSocketHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class OpenAiRealtimeApi implements RealtimeApi {
    private static final String OPENAI_REALTIME_WEBSOCKET_URL = "wss://api.openai.com/v1/realtime";
    private static final String OPENAI_BETA_HEADER = "OpenAI-Beta";
    private static final String OPENAI_BETA_VALUE = "realtime=v1";

    private static final Integer DELAY_SECONDS = 3;

    @Value("${openai.api-key}")
    private String API_KEY;

    @Value("${openai.realtime-model}")
    private String OPENAI_REALTIME_MODEL;

    private final WebSocketClient webSocketClient;
    private final MessageChannelFactory messageChannelFactory;
    private final RealtimeWebSocketHandler webSocketHandler;

    @Override
    public MessageChannel openMessageChannel(){
        try {
            return messageChannelFactory.create(openWebSocketSessionAsync().get(DELAY_SECONDS, TimeUnit.SECONDS));
        }catch (InterruptedException | ExecutionException | TimeoutException e){
            // 임시 처리
            throw new RuntimeException();
        }
    }

    private CompletableFuture<WebSocketSession> openWebSocketSessionAsync(){
        return webSocketClient.execute(webSocketHandler,
                createHttpHeaders(),
                UriComponentsBuilder.
                        fromUriString(OPENAI_REALTIME_WEBSOCKET_URL)
                        .queryParam("model", OPENAI_REALTIME_MODEL)
                        .build(true)
                        .toUri()
        );
    }

    private WebSocketHttpHeaders createHttpHeaders(){
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", API_KEY));
        headers.add(OPENAI_BETA_HEADER, OPENAI_BETA_VALUE);
        return headers;
    }
}
