package com.github.kolomolo.service.openaiclient.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kolomolo.service.openaiclient.models.requests.Message;
import com.github.kolomolo.service.openaiclient.models.responses.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Held in reserve for exceptional circumstances.
 */
@Slf4j
public class Auxiliary {
    /*
    Dummy response created from response to this request:
    {
        "model": "gpt-3.5-turbo",
        "messages": [{"role": "user", "content": "Say this is a test!"}],
        "temperature": 0.7
    }
    */
    private static final String JSON_CHAT_COMPLETION_RESPONSE = """
            {
                "id": "chatcmpl-abc123",
                "object": "chat.completion",
                "created": 1677858242,
                "model": "gpt-3.5-turbo",
                "usage": {
                    "prompt_tokens": 13,
                    "completion_tokens": 7,
                    "total_tokens": 20
                },
                "choices": [
                    {
                        "message": {
                            "role": "assistant",
                            "content": "\\n\\nThis is a test!"
                        },
                        "logprobs": null,
                        "finish_reason": "stop",
                        "index": 0
                    }
                ]
            }
            """;
    private static final String VERBOSE_JSON_TRANSCRIPTION_RESPONSE = """
            {
                "task": "transcribe",
                "language": "english",
                "duration": 8.470000267028809,
                "text": "The beach was a popular spot on a hot summer day. People were swimming in the ocean, building sandcastles, and playing beach volleyball.",
                "segments": [
                    {
                        "id": 0,
                        "seek": 0,
                        "start": 0.0,
                        "end": 3.319999933242798,
                        "text": " The beach was a popular spot on a hot summer day.",
                        "tokens": [
                            50364, 440, 7534, 390, 257, 3743, 4008, 322, 257, 2368, 4266, 786, 13, 50530
                        ],
                        "temperature": 0.0,
                        "avg_logprob": -0.2860786020755768,
                        "compression_ratio": 1.2363636493682861,
                        "no_speech_prob": 0.00985979475080967
                    }
                ]
            }
            """;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Shows the OpenAI error details.
     *
     * @param exception the {@link HttpClientErrorException}
     * @return the {@link ChatCompletionResponse}
     */
    public static ChatCompletionResponse createDummyChatResponse(HttpClientErrorException exception) {

        reportException(exception);
        final ChatCompletionResponse chatCompletionResponse;
        try {
            final JsonNode rootNode = objectMapper.readTree(JSON_CHAT_COMPLETION_RESPONSE);
            final Map<String, Object> rootMap = objectMapper.readValue(JSON_CHAT_COMPLETION_RESPONSE,
                    new TypeReference<>() {
                    });
            final Map<?, ?> usageMap = Optional.ofNullable(rootMap.get("usage"))
                    .filter(Map.class::isInstance).map(Map.class::cast).orElse(Map.of());
            final Usage usage = new Usage(usageMap.get("prompt_tokens").toString(),
                    usageMap.get("completion_tokens").toString(), usageMap.get("total_tokens").toString());
            final List<?> choiceObjList = Optional.ofNullable(rootMap.get("choices"))
                    .filter(List.class::isInstance).map(List.class::cast).orElse(List.of());
            final List<Choice> choiceList = new ArrayList<>();
            for (Object choiceObj : choiceObjList) {
                final Map<?, ?> choiceMap = Optional.ofNullable(choiceObj)
                        .filter(Map.class::isInstance).map(Map.class::cast).orElse(Map.of());
                final Map<?, ?> messageMap = Optional.ofNullable(choiceMap.get("message"))
                        .filter(Map.class::isInstance).map(Map.class::cast).orElse(Map.of());
                final Message message = new Message(
                        Optional.ofNullable(messageMap.get("role")).map(Object::toString).orElse(""),
                        Optional.ofNullable(messageMap.get("content")).map(Object::toString).orElse(""));
                choiceList.add(new Choice(Integer.valueOf(choiceMap.get("index").toString()), message,
                        choiceMap.get("finish_reason").toString()));
            }
            chatCompletionResponse = new ChatCompletionResponse(rootNode.get("id").asText(),
                    rootNode.get("object").asText(), rootNode.get("model").asText(),
                    LocalDate.now(), choiceList, usage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return chatCompletionResponse;
    }

    /**
     * Shows the OpenAI error details.
     *
     * @param exception the {@link HttpClientErrorException}
     * @return the {@link ChatCompletionResponse}
     */
    public static AudioTranscriptionResponse createDummyAudioResponse(HttpClientErrorException exception) {

        reportException(exception);
        final AudioTranscriptionResponse audioTranscriptionResponse;
        try {
            final JsonNode rootNode = objectMapper.readTree(VERBOSE_JSON_TRANSCRIPTION_RESPONSE);
            final Map<String, Object> rootMap = objectMapper.readValue(VERBOSE_JSON_TRANSCRIPTION_RESPONSE,
                    new TypeReference<>() {
                    });
            final List<?> segmentObjList = Optional.ofNullable(rootMap.get("segments"))
                    .filter(List.class::isInstance).map(List.class::cast).orElse(List.of());
            List<Segment> segmentList = new ArrayList<>();
            for (Object segmentObj : segmentObjList) {
                final Map<?, ?> segmentMap = Optional.ofNullable(segmentObj)
                        .filter(Map.class::isInstance).map(Map.class::cast).orElse(Map.of());
                final List<?> tokenObjList = Optional.ofNullable(segmentMap.get("tokens"))
                        .filter(List.class::isInstance).map(List.class::cast).orElse(List.of());
                final List<Integer> tokenList = new ArrayList<>();
                for (Object tokenObj : tokenObjList) {
                    tokenList.add(Optional.ofNullable(tokenObj).filter(Integer.class::isInstance)
                            .map(Integer.class::cast).orElse(0));
                }
                segmentList.add(new Segment(
                        Integer.parseInt(segmentMap.get("id").toString()),
                        Integer.parseInt(segmentMap.get("seek").toString()),
                        Double.parseDouble(segmentMap.get("start").toString()),
                        Double.parseDouble(segmentMap.get("end").toString()),
                        segmentMap.get("text").toString(),
                        tokenList,
                        Double.parseDouble(segmentMap.get("temperature").toString()),
                        Double.parseDouble(segmentMap.get("avg_logprob").toString()),
                        Double.parseDouble(segmentMap.get("compression_ratio").toString()),
                        Double.parseDouble(segmentMap.get("no_speech_prob").toString())));
            }
            audioTranscriptionResponse = new AudioTranscriptionResponse(
                    rootNode.get("task").asText(), rootNode.get("language").asText(),
                    rootNode.get("duration").asDouble(), rootNode.get("text").asText(), segmentList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return audioTranscriptionResponse;
    }

    /**
     * Reports the exception
     *
     * @param exception the {@link HttpClientErrorException}
     */
    public static void reportException(HttpClientErrorException exception) {

        final StringBuilder strBld = new StringBuilder();
        strBld.append(String.format("exception%n statusCode[%s], statusText[%s],%n message[%s]%n",
                exception.getStatusCode(), exception.getStatusText(),
                exception.getMessage().replace("<EOL>", "\n")));
        try {
            final Map<String, Map<String, String>> map = objectMapper.readValue(
                    exception.getResponseBodyAsString(), new TypeReference<>() {
                    });
            map.values().forEach(arg -> strBld.append(
                    String.format(" type[%s],%n message[%s]%n", arg.get("type"), arg.get("message"))));
        } catch (JsonProcessingException e) {
            log.error("reportException(): exception[{}]", e.getMessage());
        }
        log.info("reportException(): {}", strBld);

    }
}
