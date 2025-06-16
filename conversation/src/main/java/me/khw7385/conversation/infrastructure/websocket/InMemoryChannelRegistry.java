package me.khw7385.conversation.infrastructure.websocket;

import lombok.extern.slf4j.Slf4j;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.application.port.outbound.ChannelRegistry;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class InMemoryChannelRegistry implements ChannelRegistry {
    private final ConcurrentHashMap<String, MessageChannel> channelStore = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> partnerStore = new ConcurrentHashMap<>();

    @Override
    public void register(String id, String partnerId, MessageChannel channel) {
        channelStore.put(id, channel);
        partnerStore.put(id, partnerId);
        log.info("메시지 채널 등록: channelId = {}", id);
    }

    @Override
    public MessageChannel resolve(String id){
        return channelStore.get(id);
    }

    @Override
    public Optional<MessageChannel> resolvePairChannel(String id) {
        String partnerId = partnerStore.get(id);
        return Optional.ofNullable(partnerId != null ? channelStore.get(partnerId): null);
    }

    @Override
    public void unregister(String id) {
        channelStore.remove(id);
        partnerStore.remove(id);
        log.info("메시지 채널 제거: channelId = {}", id);
    }
}
