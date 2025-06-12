package me.khw7385.conversation.infrastructure.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum RealtimeEventType {
    // client
    CLIENT_INPUT_AUDIO_BUFFER_APPEND("input_audio_buffer.append"),

    // server
    ERROR("error"),

    SESSION_CREATED("session.created"),
    SESSION_UPDATED("session.updated"),

    CONVERSATION_CREATED("conversation.created"),
    CONVERSATION_ITEM_CREATED("conversation.item.created"),
    CONVERSATION_RETRIEVED("conversation.item.retrieved"),
    CONVERSATION_ITEM_INPUT_AUDIO_TRANSCRIPTION_COMPLETED("conversation.item.input_audio_transcription.completed"),
    CONVERSATION_ITEM_INPUT_AUDIO_TRANSCRIPTION_DELTA("conversation.item.input_audio_transcription.delta"),
    CONVERSATION_ITEM_INPUT_AUDIO_TRANSCRIPTION_FAILED("conversation.item.input_audio_transcription.failed"),
    CONVERSATION_ITEM_TRUNCATED("conversation.item.truncated"),
    CONVERSATION_ITEM_DELETED("conversation.item.deleted"),

    INPUT_AUDIO_BUFFER_COMMITTED("input_audio_buffer.committed"),
    INPUT_AUDIO_BUFFER_CLEARED("input_audio_buffer.cleared"),
    INPUT_AUDIO_BUFFER_SPEECH_STARTED("input_audio_buffer.speech_started"),
    INPUT_AUDIO_BUFFER_SPEECH_STOPPED("input_audio_buffer.speech_stopped"),

    RESPONSE_CREATED("response.created"),
    RESPONSE_DONE("response.done"),
    RESPONSE_OUTPUT_ITEM_ADDED("response.output_item.added"),
    RESPONSE_OUTPUT_ITEM_DONE("response.output_item.done"),
    RESPONSE_CONTENT_PART_ADDED("response.content_part.added"),
    RESPONSE_CONTENT_PART_DONE("response.content_part.done"),
    RESPONSE_TEXT_DELTA("response.text.delta"),
    RESPONSE_TEXT_DONE("response.text.done"),
    RESPONSE_AUDIO_TRANSCRIPT_DELTA("response.audio_transcript.delta"),
    RESPONSE_AUDIO_TRANSCRIPT_DONE("response.audio_transcript.done"),
    RESPONSE_AUDIO_DELTA("response.audio.delta"),
    RESPONSE_AUDIO_DONE("response.audio.done"),
    RESPONSE_FUNCTION_CALL_ARGUMENTS_DELTA("response.function_call_arguments.delta"),
    RESPONSE_FUNCTION_CALL_ARGUMENTS_DONE("response.function_call_arguments.done"),

    TRANSCRIPTION_SESSION_UPDATED("transcript_session.updated"),

    RATE_LIMITS_UPDATED("rate_limits_updated");

    private final String value;

    RealtimeEventType(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return this.value;
    }

    @JsonCreator
    public static RealtimeEventType fromString(String value){
        return Arrays.stream(RealtimeEventType.values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow();
    }
}
