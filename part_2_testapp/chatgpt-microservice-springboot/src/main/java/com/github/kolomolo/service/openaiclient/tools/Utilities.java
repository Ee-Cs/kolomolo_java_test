package com.github.kolomolo.service.openaiclient.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.kolomolo.service.openaiclient.models.requests.AudioRequest;
import com.github.kolomolo.service.openaiclient.models.requests.Message;
import com.github.kolomolo.service.openaiclient.models.responses.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.github.kolomolo.service.openaiclient.Constants.*;

/**
 * The utilities.
 */
@Slf4j
public class Utilities {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, true);
    /**
     * Absolutely do not use this solution on production!
     */
    private static File DEVELOPER_SAMPLE_FILE = null;

    /**
     * Creates temporary sample file for upload.
     *
     * @param audioRequest the {@link AudioRequest}
     * @return the optional with the sample file
     */
    public static Optional<File> createSampleFile(AudioRequest audioRequest) {

        final Optional<MultipartFile> multipartFileOpt =
                Optional.ofNullable(audioRequest).map(AudioRequest::getFile);
        if (multipartFileOpt.isEmpty()) {
            DEVELOPER_SAMPLE_FILE = null;
            log.warn("createSampleFile(): file is absent");
            return Optional.empty();
        }
        try {
            byte[] contents = multipartFileOpt.get().getBytes();
            final Path path = Files.createTempFile(
                    Path.of(System.getProperty(TMP_DIR_KEY)), PREFIX, SUFFIX);
            try (OutputStream outputStream = Files.newOutputStream(path)) {
                outputStream.write(contents);
            }
            DEVELOPER_SAMPLE_FILE = path.toFile();
            return Optional.of(path.toFile());
        } catch (IOException e) {
            log.error("createSampleFile(): JsonProcessingException[{}]", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Gets last used temporary sample file for upload.
     *
     * @return the optional with the sample file
     */
    public static Optional<File> getSampleFile() {
        return Optional.ofNullable(DEVELOPER_SAMPLE_FILE);
    }

    /**
     * Creates empty {@link ChatCompletionResponse}.
     *
     * @return the response
     */
    public static ChatCompletionResponse createEmptyChatCompletionResponse() {

        return new ChatCompletionResponse("", "", "", LocalDate.EPOCH,
                List.of(new Choice(1, new Message("", ""),"")),
                new Usage("", "", ""));
    }

    /**
     * Creates empty {@link AudioTranscriptionResponse}.
     *
     * @return the response
     */
    public static AudioTranscriptionResponse createEmptyAudioTranscriptionResponse() {
        return new AudioTranscriptionResponse("", "", 0d, "",
                List.of(new Segment(0, 0, 0d, 0d, "", List.of(0),
                        0d, 0d,  0d, 0d)));
    }

    /**
     * Creates JSON with indentation.
     *
     * @param chatCompletionResponse the {@link ChatCompletionResponse}
     * @return the JSON with indentation.
     */
    public static String toPrettyJsonChatGPTResponse(
            ChatCompletionResponse chatCompletionResponse) {

        try {
            return objectMapper.writeValueAsString(chatCompletionResponse);
        } catch (JsonProcessingException e) {
            log.error("toPrettyJsonChatGPTResponse(): JsonProcessingException[{}]",
                    e.getMessage());
            return "";
        }
    }

    /**
     * Creates JSON with indentation.
     *
     * @param audioTranscriptionResponse the {@link AudioTranscriptionResponse}
     * @return the JSON with indentation.
     */
    public static String toPrettyJsonAudioTranscriptionResponse(
            AudioTranscriptionResponse audioTranscriptionResponse) {

        try {
            return objectMapper.writeValueAsString(audioTranscriptionResponse);
        } catch (JsonProcessingException e) {
            log.error("toPrettyJsonAudioTranscriptionResponse(): JsonProcessingException[{}]",
                    e.getMessage());
            return "";
        }
    }

    /**
     * Creates JSON with indentation.
     *
     * @param messagesList the list with {@link Message}
     * @return the JSON with indentation.
     */
    public static String toPrettyJsonMessages(List<Message> messagesList) {

        try {
            return objectMapper.writeValueAsString(messagesList);
        } catch (JsonProcessingException e) {
            log.error("toPrettyJsonMessages(): JsonProcessingException[{}]", e.getMessage());
            return "";
        }
    }

    /**
     * Replaces the OpenAI API key.
     *
     * @param apiKey the API key
     * @return the OpenAI API key.
     */
    public static String replaceApiKey(String apiKey) {

        if (!SECRET_FILE.toFile().canRead()) {
            log.error("replaceApiKey(): unreadable secret file[{}]", SECRET_FILE);
            return apiKey;
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(SECRET_FILE)) {
            log.warn("replaceApiKey(): key from properties is replaced with key from secret file");
            return bufferedReader.readLine();
        } catch (IOException e) {
            log.error("replaceApiKey(): IOException[{}]%n", e.getMessage());
            return apiKey;
        }
    }

}