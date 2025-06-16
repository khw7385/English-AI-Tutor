package me.khw7385.conversation.application;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.port.inbound.AudioStreamingUseCase;
import me.khw7385.conversation.application.port.outbound.Message;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.application.port.outbound.ChannelRegistry;
import me.khw7385.conversation.core.exception.MessageChannelNotFoundException;
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
        MessageChannel channel = channelRegistry.resolvePairChannel(id).orElseThrow(MessageChannelNotFoundException::new);
        channel.sendAudioMessage(message);
    }

    @Override
    public void releaseChannel(String id) {
        MessageChannel channel = channelRegistry.resolve(id);
        channelRegistry.unregister(id);
        channel.close();
    }

    @Override
    public void releasePairChannel(String id){
        channelRegistry.resolvePairChannel(id).ifPresent(channel -> {
            if(channel.isOpen()) channel.close();
            channelRegistry.unregister(channel.getId());
        });
    }
}
