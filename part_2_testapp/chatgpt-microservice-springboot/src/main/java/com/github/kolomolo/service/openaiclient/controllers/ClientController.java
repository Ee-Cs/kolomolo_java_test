package com.github.kolomolo.service.openaiclient.controllers;

import com.github.kolomolo.service.openaiclient.models.requests.AudioRequest;
import com.github.kolomolo.service.openaiclient.models.requests.ChatRequest;
import com.github.kolomolo.service.openaiclient.models.responses.AudioTranscriptionResponse;
import com.github.kolomolo.service.openaiclient.models.responses.ChatCompletionResponse;
import com.github.kolomolo.service.openaiclient.services.ClientService;
import com.github.kolomolo.service.openaiclient.tools.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Optional;

/**
 * The client controller.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class ClientController {

    private final ClientService clientService;

    /**
     * The constructor.
     *
     * @param clientService the {@link ClientService}
     */
    public ClientController(@Autowired ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Handles the chat completion using OpenAI ChatGPT.
     *
     * @param chatRequest the {@link ChatRequest}
     * @return the {@link ChatCompletionResponse}
     */
    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatCompletionResponse handleChatCompletion(@RequestBody ChatRequest chatRequest) {

        final ChatCompletionResponse chatGPTResponse =
                clientService.processChat(chatRequest.getQuestion());
        log.info("handleChatCompletion(): question[{}]", chatRequest.getQuestion());
        return chatGPTResponse;
    }

    /**
     * Handles the audio transcription using OpenAI Whisper.
     *
     * @param audioRequest the {@link AudioRequest}
     * @return the {@link AudioTranscriptionResponse}
     */
    @PostMapping(value = "/transcription", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AudioTranscriptionResponse handleAudioTranscription(
            @ModelAttribute AudioRequest audioRequest) {

        final Optional<File> sampleFileOpt = Utilities.createSampleFile(audioRequest);
        if (sampleFileOpt.isEmpty()) {
            log.error("handleAudioTranscription(): received empty or unreadable file");
            return Utilities.createEmptyAudioTranscriptionResponse();
        }
        final AudioTranscriptionResponse audioTranscriptionResponse =
                clientService.processAudio(sampleFileOpt.get());
        log.info("handleAudioTranscription(): sample file name[{}]", sampleFileOpt.get().getName());
        return audioTranscriptionResponse;
    }
    /**
     * Handles the audio transcription using OpenAI Whisper - repeating with last used sample.
     *
     * @return the {@link AudioTranscriptionResponse}
     */
    @GetMapping(value = "/transcription_repeat")
    public String handleAudioTranscriptionRepeat() {

        final Optional<File> sampleFileOpt = Utilities.getSampleFile();
        if (sampleFileOpt.isEmpty()) {
            log.error("handleAudioTranscriptionRepeat(): received empty or unreadable file");
            return "";
        }
        final AudioTranscriptionResponse audioTranscriptionResponse =
                clientService.processAudio(sampleFileOpt.get());
        log.info("handleAudioTranscriptionRepeat(): sample file name[{}]", sampleFileOpt.get().getName());
        return audioTranscriptionResponse.getText();
    }
}
