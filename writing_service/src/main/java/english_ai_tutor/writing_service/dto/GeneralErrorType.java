package english_ai_tutor.writing_service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.swing.text.StyledEditorKit;
import java.util.Arrays;

public enum GeneralErrorType {
    GRAMMAR("Grammar"),
    SPELLING("Spelling"),
    PUNCTUATION("Punctuation");

    private String name;

    GeneralErrorType(String name) {
        this.name = name;
    }

    @JsonCreator
    public static GeneralErrorType of(String error){
        return Arrays.stream(GeneralErrorType.values())
                .filter(g -> g.name.equals(error))
                .findAny()
                .orElse(null);
    }
}
