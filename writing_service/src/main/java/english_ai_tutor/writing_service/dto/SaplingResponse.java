package english_ai_tutor.writing_service.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaplingResponse {
    private List<Edit> edits;
}
