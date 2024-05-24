package english_ai_tutor.writing_service.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Sentence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "korean_sentence")
    private String Korean;

    @Column(name = "english_sentence")
    private String English;

    private Integer level;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    @Builder
    public Sentence(String korean, String english, Integer level, News news) {
        this.Korean = korean;
        this.English = english;
        this.level = level;
        this.news = news;
    }
}
