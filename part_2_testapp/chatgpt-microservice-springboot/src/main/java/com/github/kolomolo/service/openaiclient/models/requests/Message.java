package com.github.kolomolo.service.openaiclient.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * The Message.
 */
@Data
@AllArgsConstructor
public class Message implements Serializable {
    private String role;
    private String content;
}
