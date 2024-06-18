package com.github.kolomolo.service.openaiclient.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The ChatGPT Request.
 */
@Data
public class ChatGPTRequest implements Serializable {
    private String model;
    private List<Message> messages;
}
