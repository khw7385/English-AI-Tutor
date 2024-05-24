package english_ai_tutor.writing_service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SentenceDto {
    private String Korean;
    private String English;
    private Integer level;
    @Builder
    public SentenceDto(String korean, String english, Integer level) {
        this.Korean = korean;
        this.English = english;
        this.level = level;
    }
}
