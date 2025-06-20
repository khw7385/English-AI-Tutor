package me.khw7385.conversation.core.exception;

public class ConversationNotFoundException extends BusinessException {
    public ConversationNotFoundException(Long id) {
        super(String.format("해당 id를 가진 Conversation이 존재하지 않습니다. id = %d", id));
    }
}
