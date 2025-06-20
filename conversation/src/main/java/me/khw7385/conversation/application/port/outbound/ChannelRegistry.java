package me.khw7385.conversation.application.port.outbound;

import java.util.Optional;

public interface ChannelRegistry {
    void register(String id, String partnerId, MessageChannel channel);
    Optional<MessageChannel> resolve(String id);
    Optional<MessageChannel> resolvePairChannel(String id);
    void unregister(String id);
}
