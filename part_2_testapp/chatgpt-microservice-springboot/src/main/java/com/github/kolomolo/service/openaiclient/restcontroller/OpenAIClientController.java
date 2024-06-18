package com.github.kolomolo.service.openaiclient.restcontroller;

import com.github.kolomolo.service.openaiclient.model.request.ChatGPTRequest;
import com.github.kolomolo.service.openaiclient.model.request.ChatRequest;
import com.github.kolomolo.service.openaiclient.model.request.TranscriptionRequest;
import com.github.kolomolo.service.openaiclient.model.request.WhisperTranscriptionRequest;
import com.github.kolomolo.service.openaiclient.model.response.ChatGPTResponse;
import com.github.kolomolo.service.openaiclient.model.response.WhisperTranscriptionResponse;
import com.github.kolomolo.service.openaiclient.service.OpenAIClientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class OpenAIClientController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    private final OpenAIClientService openAIClientService;

//FIXME it was only 'import lombok.RequiredArgsConstructor' but annotation was not used
//    /**
//     * The constructor.
//     *
//     * @param openAIClientService the {@link OpenAIClientService}
//     */
//    public OpenAIClientController(OpenAIClientService openAIClientService) {
//        this.openAIClientService = openAIClientService;
//    }

    /**
     * Chats.
     *
     * @param chatRequest the {@link ChatRequest}
     * @return the {@link ChatGPTResponse}
     */
    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatGPTResponse chat(@RequestBody ChatRequest chatRequest) {

        final String question = chatRequest.getQuestion();

        final ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        chatGPTRequest.setMessages(new ArrayList<>());
        chatGPTRequest.setModel("the model");
        final ChatGPTResponse chatGPTResponse = openAIClientService.chat(chatGPTRequest);
        logger.info("chat(): question[{}], messages[{}], model[{}]",
                question, chatGPTRequest.getMessages(), chatGPTRequest.getModel());
        return chatGPTResponse;
    }

    /**
     * @param transcriptionRequest the {@link TranscriptionRequest}
     * @return the {@link WhisperTranscriptionResponse}
     */
    @PostMapping(value = "/transcription", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WhisperTranscriptionResponse createTranscription(@ModelAttribute TranscriptionRequest transcriptionRequest) {

        final MultipartFile multipartFile = transcriptionRequest.getFile();
        final WhisperTranscriptionRequest whisperTranscriptionRequest = new WhisperTranscriptionRequest();
        whisperTranscriptionRequest.setFile(multipartFile);
        whisperTranscriptionRequest.setModel("the model");
        final WhisperTranscriptionResponse whisperTranscriptionResponse =
                openAIClientService.createTranscription(whisperTranscriptionRequest);
        logger.info("createTranscription(): multipart file name[{}], model[{}]",
                multipartFile.getName(), whisperTranscriptionRequest.getModel());
        return whisperTranscriptionResponse;
    }
}
