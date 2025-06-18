package me.khw7385.conversation.application;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.domain.Conversation;
import me.khw7385.conversation.domain.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConversationFacade {
    private final ConversationRepository conversationRepository;

    public String findPrompt(Long themeId){
        Conversation conversation = conversationRepository.findById(themeId)
                .orElseThrow();
        return conversation.getPrompt();
    }
}
