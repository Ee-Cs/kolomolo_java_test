package com.github.kolomolo.service.openaiclient.openaiclient;

import com.github.kolomolo.service.openaiclient.model.request.ChatGPTRequest;
import com.github.kolomolo.service.openaiclient.model.request.WhisperTranscriptionRequest;
import com.github.kolomolo.service.openaiclient.model.response.ChatGPTResponse;
import com.github.kolomolo.service.openaiclient.model.response.WhisperTranscriptionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The OpenAI client.
 */
@FeignClient(
        name = "openai-service",
        url = "${openai-service.urls.base-url}",
        configuration = OpenAIClientConfig.class
)
public interface OpenAIClient {
    /**
     * Chats.
     *
     * @param chatGPTRequest the ChatGPT request
     * @return the ChatGPT response
     */
    @PostMapping(value = "${openai-service.urls.chat-url}",
            headers = {"Content-Type=application/json"})
    ChatGPTResponse chat(@RequestBody ChatGPTRequest chatGPTRequest);

    /**
     * Creates the transcription.
     *
     * @param whisperTranscriptionRequest the whisper transcription request
     * @return the whisper transcription response
     */
    @PostMapping(value = "${openai-service.urls.create-transcription-url}",
            headers = {"Content-Type=multipart/form-data"})
    WhisperTranscriptionResponse createTranscription(
            @ModelAttribute WhisperTranscriptionRequest whisperTranscriptionRequest);
}
