package english_ai_tutor.writing_service.repository;

import english_ai_tutor.writing_service.domain.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {
}
