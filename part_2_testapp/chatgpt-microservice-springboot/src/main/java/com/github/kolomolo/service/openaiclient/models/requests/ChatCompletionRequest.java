package com.github.kolomolo.service.openaiclient.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The chat completion request.
 */
@Data
@AllArgsConstructor
public class ChatCompletionRequest implements Serializable {
    private String model;
    private List<Message> messages;
}
