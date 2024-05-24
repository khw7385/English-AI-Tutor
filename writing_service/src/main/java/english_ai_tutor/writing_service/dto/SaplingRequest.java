package english_ai_tutor.writing_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Builder
@NoArgsConstructor
public class SaplingRequest {
    private String key;
    private String text;
    private String session_id;

    public SaplingRequest(String key, String text, String session_id) {
        this.key = key;
        this.text = text;
        this.session_id = session_id;
    }
}
