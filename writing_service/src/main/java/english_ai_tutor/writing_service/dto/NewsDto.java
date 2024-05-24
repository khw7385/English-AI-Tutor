package english_ai_tutor.writing_service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NewsDto {
    private String title;

    @Builder
    public NewsDto(String title) {
        this.title = title;
    }
}
