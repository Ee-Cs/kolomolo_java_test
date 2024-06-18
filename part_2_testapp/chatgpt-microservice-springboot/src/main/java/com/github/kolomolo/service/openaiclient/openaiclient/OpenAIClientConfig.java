package com.github.kolomolo.service.openaiclient.openaiclient;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;

import java.util.concurrent.TimeUnit;

/**
 * The OpenAI client configuration.
 */
@Configuration
@ConfigurationProperties
@Indexed
@Data
@Slf4j
public class OpenAIClientConfig {

    @Value("${openai-service.http-client.read-timeout}")
    private int readTimeout;

    @Value("${openai-service.http-client.connect-timeout}")
    private int connectTimeout;

    @Value("${openai-service.api-key}")
    private String apiKey;

    @Value("${openai-service.gpt-model}")
    private String model;

    @Value("${openai-service.audio-model}")
    private String audioModel;

    /**
     * Creates the {@link Request.Options} bean.
     *
     * @return the {@link Request.Options}
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(getConnectTimeout(), TimeUnit.MILLISECONDS,
                getReadTimeout(), TimeUnit.MILLISECONDS, true);
    }

    /**
     * Creates the {@link Logger.Level} bean.
     *
     * @return the {@link Logger.Level}
     */
    @Bean
    public Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }

    /**
     * Creates the {@link Retryer} bean.
     *
     * @return the {@link Retryer}
     */
    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

    /**
     * Creates the 'apiKeyInterceptor' bean.
     *
     * @return the apiKeyInterceptor
     */
    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return request -> request.header("Authorization", "Bearer " + apiKey);
    }
}
