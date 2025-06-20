package me.khw7385.conversation.core.exception;

public class MessageChannelNotFoundException extends BusinessException {
    public MessageChannelNotFoundException() {
        super("Message Channel이 존재하지 않습니다.");
    }
}
