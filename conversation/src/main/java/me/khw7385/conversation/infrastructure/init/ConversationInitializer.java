package me.khw7385.conversation.infrastructure.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.domain.repository.ConversationRepository;
import org.springframework.stereotype.Component;

import static me.khw7385.conversation.domain.Conversation.*;

@Component
@RequiredArgsConstructor
public class ConversationInitializer {
    private final ConversationRepository conversationRepository;

    @PostConstruct
    public void init(){
        conversationRepository.save(
                create(
                        "패스트 푸드점에서 대화하기",
                        """
                        Let's do a role-play.
                        You can pretend to be a fast food clerk.
                        You don't have to ask me questions all the time.
                        Try to keep the conversation as realistic as possible. Response are made in less than 200 characters.
                        """
                        ));
    }
}
