package me.khw7385.conversation.infrastructure.websocket;

import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.application.port.outbound.ChannelRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryChannelRegistry implements ChannelRegistry {
    private final ConcurrentHashMap<String, MessageChannel> sessionStore = new ConcurrentHashMap<>();

    @Override
    public void register(String id, MessageChannel channel) {
        sessionStore.put(id, channel);
    }

    @Override
    public MessageChannel resolve(String id) {
        return sessionStore.get(id);
    }

    @Override
    public void unregister(String id) {
        sessionStore.remove(id);
    }
}
