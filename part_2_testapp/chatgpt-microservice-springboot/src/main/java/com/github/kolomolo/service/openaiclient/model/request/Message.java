package com.github.kolomolo.service.openaiclient.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * The Message.
 */
@Data
public class Message implements Serializable {
    private String role;
    private String content;
}
