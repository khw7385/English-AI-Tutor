package english_ai_tutor.writing_service.service;

import com.deepl.api.DeepLException;
import com.deepl.api.Translator;
import english_ai_tutor.writing_service.dto.SentenceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationService {
    private Translator translator;

    public TranslationService(@Value("${deepl.api.key}")String key) {
        this.translator = new Translator(key);
    }

    public List<SentenceDto> translate(List<String> englishSentences, Integer level){

        return englishSentences.stream().map(english -> {
            String korean;
            try {
                korean = translator.translateText(english, null, "KO").getText();
            } catch (DeepLException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return SentenceDto.builder()
                    .english(english)
                    .korean(korean)
                    .level(level)
                    .build();
        }).toList();
    }
}
