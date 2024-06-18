package com.github.kolomolo.service.openaiclient.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.kolomolo.service.openaiclient.tools.Utilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.Collections;

/**
 * The client configuration.
 */
@Configuration
public class ClientConfiguration {

    /**
     * Creates the {@link MappingJackson2HttpMessageConverter} bean.
     *
     * @return the converter
     */
    @Bean
    MappingJackson2HttpMessageConverter converter() {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        final MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter(mapper);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        return converter;
    }

    /**
     * Creates the {@link RestClient} bean.
     *
     * @param openAIConfiguration the {@link OpenAIConfiguration}
     * @param converter           the {@link MappingJackson2HttpMessageConverter}
     * @return the rest client
     */
    @Bean
    RestClient restClient(OpenAIConfiguration openAIConfiguration,
                          MappingJackson2HttpMessageConverter converter) {

        return RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(openAIConfiguration.getBaseUrl())
                .defaultHeader("Authorization",
                        "Bearer " + Utilities.replaceApiKey(openAIConfiguration.getApiKey()))
                .messageConverters(list -> list.add(converter))
                .build();
    }

}
