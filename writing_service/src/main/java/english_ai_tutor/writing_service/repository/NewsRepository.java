package english_ai_tutor.writing_service.repository;

import english_ai_tutor.writing_service.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
