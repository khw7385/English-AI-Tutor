package me.khw7385.conversation.application.port.outbound;

public interface MessageChannel {
    String getId();
    void sendAudioMessage(Message message);
    void close();
}
