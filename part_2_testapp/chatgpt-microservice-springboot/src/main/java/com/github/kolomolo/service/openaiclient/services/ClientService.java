package com.github.kolomolo.service.openaiclient.services;

import com.github.kolomolo.service.openaiclient.configuration.OpenAIConfiguration;
import com.github.kolomolo.service.openaiclient.models.requests.ChatCompletionRequest;
import com.github.kolomolo.service.openaiclient.models.requests.Message;
import com.github.kolomolo.service.openaiclient.models.responses.ChatCompletionResponse;
import com.github.kolomolo.service.openaiclient.models.responses.AudioTranscriptionResponse;
import com.github.kolomolo.service.openaiclient.tools.Auxiliary;
import com.github.kolomolo.service.openaiclient.tools.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

/**
 * The client service.
 */
@Slf4j
@Service
public class ClientService {
    private final RestClient restClient;
    private final OpenAIConfiguration openAIConfiguration;

    /**
     * The constructor.
     *
     * @param restClient          the {@link RestClient}
     * @param openAIConfiguration the {@link OpenAIConfiguration}
     */
    public ClientService(RestClient restClient, OpenAIConfiguration openAIConfiguration) {
        this.restClient = restClient;
        this.openAIConfiguration = openAIConfiguration;
    }

    /**
     * Processes the chat.
     *
     * @param question the question
     * @return the {@link ChatCompletionResponse}
     */
    public ChatCompletionResponse processChat(String question) {

        if (question.isBlank()) {
            log.warn("processChat(): question is absent");
            return Utilities.createEmptyChatCompletionResponse();
        }
        final ChatCompletionRequest chatGPTRequest = createChatCompletionRequest(question);
        ChatCompletionResponse chatCompletionResponse;
        try {
            chatCompletionResponse = restClient.post()
                    .uri(openAIConfiguration.getChatCompletionsUrl())
                    .contentType(APPLICATION_JSON)
                    .body(chatGPTRequest)
                    .retrieve()
                    /* FIXME THIS
                    .onStatus(status -> status.value() == 404, (request, response) -> {
                        DO something
                    })
                    */
                    .body(ChatCompletionResponse.class);
        } catch (HttpClientErrorException exception) {
            chatCompletionResponse = Auxiliary.createDummyChatResponse(exception);
        }
        log.info("processChat():\n messages{},\n model[{}],\n chatCompletionResponse[ {} ]",
                Utilities.toPrettyJsonMessages(chatGPTRequest.getMessages()), chatGPTRequest.getModel(),
                Utilities.toPrettyJsonChatGPTResponse(chatCompletionResponse));
        return chatCompletionResponse;
    }

    /**
     * Processes the audio.
     *
     * @param sampleFile the sample file
     * @return the {@link AudioTranscriptionResponse}
     */
    public AudioTranscriptionResponse processAudio(File sampleFile) {

        AudioTranscriptionResponse audioTranscriptionResponse;
        try {
            audioTranscriptionResponse = restClient.post()
                    .uri(openAIConfiguration.getAudioTranscriptionUrl())
                    .contentType(MULTIPART_FORM_DATA)
                    .body(createAudioTranscriptionRequestBody(sampleFile))
                    .retrieve()
                    .body(AudioTranscriptionResponse.class);
        } catch (HttpClientErrorException exception) {
            audioTranscriptionResponse = Auxiliary.createDummyAudioResponse(exception);
        }
        log.info("processAudio():\n audioTranscriptionResponse[ {} ]",
                Utilities.toPrettyJsonAudioTranscriptionResponse(audioTranscriptionResponse));
        return audioTranscriptionResponse;
    }

    /**
     * Creates  the {@link ChatCompletionRequest}.
     *
     * @param question the question
     * @return the {@link ChatCompletionRequest}
     */
    private ChatCompletionRequest createChatCompletionRequest(String question) {

        return new ChatCompletionRequest(openAIConfiguration.getGptModel(), List.of(
                new Message("system", "You are a helpful assistant."),
                new Message("user", question)));
    }

    /**
     * Creates the audio transcription request body.
     *
     * @param sampleFile the sample file
     * @return the transcription request body
     */
    private MultiValueMap<String, Object> createAudioTranscriptionRequestBody(File sampleFile) {

        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("model", openAIConfiguration.getAudioModel());
        body.add("file", new FileSystemResource(sampleFile));
        return body;
    }

}
