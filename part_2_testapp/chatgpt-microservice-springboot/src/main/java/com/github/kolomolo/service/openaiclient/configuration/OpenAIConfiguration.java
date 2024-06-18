package com.github.kolomolo.service.openaiclient.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The OpenAI configuration read from properties.
 */
@Data
@ConfigurationProperties
@Configuration
public class OpenAIConfiguration {
    @NotBlank
    @Value("${openai-service.api-key}")
    private String apiKey;

    @NotBlank
    @Value("${openai-service.gpt-model}")
    private String gptModel;

    @NotBlank
    @Value("${openai-service.audio-model}")
    private String audioModel;

    @Min(1)
    @Max(60_000)
    @Value("${openai-service.http-client.read-timeout}")
    private int readTimeout;

    @Min(1)
    @Max(60_000)
    @Value("${openai-service.http-client.connect-timeout}")
    private int connectTimeout;

    @NotBlank
    @Value("${openai-service.urls.base-url}")
    private String baseUrl;

    @NotBlank
    @Value("${openai-service.urls.chat.completions-url}")
    private String chatCompletionsUrl;

    @NotBlank
    @Value("${openai-service.urls.audio.transcriptions-url}")
    private String audioTranscriptionUrl;
}
