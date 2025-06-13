package me.khw7385.conversation.application.port.inbound;

import me.khw7385.conversation.application.port.outbound.Message;
import me.khw7385.conversation.application.port.outbound.MessageChannel;

public interface AudioStreamingUseCase {
    void connect(String id, MessageChannel channel);
    void forward(String id, Message message);
    void close(String id);
}
