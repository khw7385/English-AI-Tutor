package me.khw7385.conversation.domain.repository;

import me.khw7385.conversation.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
