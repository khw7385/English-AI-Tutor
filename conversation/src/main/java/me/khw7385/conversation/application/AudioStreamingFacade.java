package me.khw7385.conversation.application;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.port.inbound.AudioStreamingUseCase;
import me.khw7385.conversation.application.port.outbound.Message;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.application.port.outbound.ChannelRegistry;
import me.khw7385.conversation.infrastructure.OpenAiRealtimeApi;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AudioStreamingFacade implements AudioStreamingUseCase {
    private final ChannelRegistry channelRegistry;

    private final OpenAiRealtimeApi realtimeApi;

    @Override
    public void connect(String id, MessageChannel clientChannel){
        MessageChannel aiChannel = realtimeApi.openMessageChannel();
        channelRegistry.register(id, aiChannel.getId(), clientChannel);
        channelRegistry.register(aiChannel.getId(), id, aiChannel);
    }

    @Override
    public void forward(String id, Message message){
        MessageChannel channel = sessionRegistry.resolve(id);
        channel.sendAudioMessage(message);
    }

    @Override
    public void close(String id){
        MessageChannel aiChannel = sessionRegistry.resolve(id);

        aiChannel.close();
        sessionRegistry.unregister(id);
        sessionRegistry.unregister(aiChannel.getId());
    }
}
