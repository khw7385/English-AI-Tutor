package english_ai_tutor.writing_service.dto;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Edit {
    private Integer start;
    private Integer end;
    private String replacement;
    private String error_type;
    private GeneralErrorType general_error_type;
}
