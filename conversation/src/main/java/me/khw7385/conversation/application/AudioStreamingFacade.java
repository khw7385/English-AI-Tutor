package me.khw7385.conversation.application;

import lombok.RequiredArgsConstructor;
import me.khw7385.conversation.application.port.inbound.AudioStreamingUseCase;
import me.khw7385.conversation.application.port.outbound.ChannelRegistry;
import me.khw7385.conversation.application.port.outbound.Message;
import me.khw7385.conversation.application.port.outbound.MessageChannel;
import me.khw7385.conversation.core.exception.MessageChannelNotFoundException;
import me.khw7385.conversation.infrastructure.OpenAiRealtimeApi;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AudioStreamingFacade implements AudioStreamingUseCase {
    private final ChannelRegistry channelRegistry;

    private final OpenAiRealtimeApi realtimeApi;

    @Override
    public void connect(String channelId, MessageChannel clientChannel){
        MessageChannel aiChannel = realtimeApi.openMessageChannel();

        channelRegistry.register(channelId, aiChannel.getId(), clientChannel);
        channelRegistry.register(aiChannel.getId(), channelId, aiChannel);
    }

    @Override
    public void forward(String channelId, Message message){
        MessageChannel channel = channelRegistry.resolvePairChannel(channelId).orElseThrow(MessageChannelNotFoundException::new);
        channel.sendAudioMessage(message);
    }

    @Override
    public void cleanUp(String channelId){
        channelRegistry.resolve(channelId).ifPresent(channel -> {
            MessageChannel partnerChannel = channelRegistry.resolvePairChannel(channelId).orElseThrow(MessageChannelNotFoundException::new);
            channelRegistry.unregister(channelId);
            channelRegistry.unregister(partnerChannel.getId());

            if(partnerChannel.isOpen()) partnerChannel.close();
        });
    }
}
