package me.khw7385.conversation.infrastructure;

import me.khw7385.conversation.config.RestClientConfig;
import me.khw7385.conversation.domain.RealtimeSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {RestClientConfig.class, OpenAiSessionCreationApi.class})
class OpenAiSessionCreationApiTest {

    @Autowired
    private OpenAiSessionCreationApi realtimeApi;

    @Test
    void createSession_success(){
        RealtimeSession session = realtimeApi.createSession();

        assertNotNull(session.getClientId());
        assertNotNull(session.getExpiresAt());
    }
}