package me.khw7385.conversation.application.port.outbound;

public interface ChannelRegistry {
    void register(String id, String partnerId, MessageChannel channel);
    MessageChannel resolve(String id);
    void unregister(String id);
}
