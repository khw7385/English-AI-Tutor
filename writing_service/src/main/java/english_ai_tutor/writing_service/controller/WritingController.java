package english_ai_tutor.writing_service.controller;

import english_ai_tutor.writing_service.dto.GrammarSpellDto;
import english_ai_tutor.writing_service.service.SaplingAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/writing")
@RequiredArgsConstructor
public class WritingController {
    private final SaplingAiClient saplingAiClient;

    @PostMapping("/check/grammar-and-spell")
    public ResponseEntity<?> checkGrammarAndSpell(@RequestBody GrammarSpellDto.Request request){

        return ResponseEntity.ok().body(saplingAiClient.checkGrammarAndSpell(request.toCommand()));
    }
}
