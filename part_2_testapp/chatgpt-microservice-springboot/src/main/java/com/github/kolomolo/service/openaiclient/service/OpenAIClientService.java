package com.github.kolomolo.service.openaiclient.service;

import com.github.kolomolo.service.openaiclient.model.request.ChatGPTRequest;
import com.github.kolomolo.service.openaiclient.model.request.WhisperTranscriptionRequest;
import com.github.kolomolo.service.openaiclient.model.response.ChatGPTResponse;
import com.github.kolomolo.service.openaiclient.model.response.WhisperTranscriptionResponse;
import com.github.kolomolo.service.openaiclient.openaiclient.OpenAIClient;
import com.github.kolomolo.service.openaiclient.openaiclient.OpenAIClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

/**
 * The OpenAI client service
 */
@Service
public class OpenAIClientService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    private final OpenAIClient openAIClient;
    private final OpenAIClientConfig openAIClientConfig;

    /**
     * The constructor.
     *
     * @param openAIClient       the {@link OpenAIClient}
     * @param openAIClientConfig the {@link OpenAIClientConfig}
     */
    public OpenAIClientService(OpenAIClient openAIClient, OpenAIClientConfig openAIClientConfig) {
        this.openAIClient = openAIClient;
        this.openAIClientConfig = openAIClientConfig;
    }

    /**
     * Chats.
     *
     * @param chatGPTRequest the {@link ChatGPTRequest}
     * @return the {@link ChatGPTResponse}
     */
    public ChatGPTResponse chat(ChatGPTRequest chatGPTRequest) {
        // todo - need to call chatgpt

        final ChatGPTResponse chatGPTResponse = openAIClient.chat(chatGPTRequest);
        logger.info("chat(): messages[{}], model[{}]",
                chatGPTRequest.getMessages(), chatGPTRequest.getModel());
        return chatGPTResponse;
    }

    /**
     * @param whisperTranscriptionRequest the {@link WhisperTranscriptionRequest}
     * @return the {@link WhisperTranscriptionResponse}
     */
    public WhisperTranscriptionResponse createTranscription(WhisperTranscriptionRequest whisperTranscriptionRequest) {
        // todo - need to build a message for whisper api
        final WhisperTranscriptionResponse whisperTranscriptionResponse =
                openAIClient.createTranscription(whisperTranscriptionRequest);
        logger.info("createTranscription(): multipart file name[{}], model[{}]",
                whisperTranscriptionRequest.getFile().getName(), whisperTranscriptionRequest.getModel());
        return whisperTranscriptionResponse;
    }
}
