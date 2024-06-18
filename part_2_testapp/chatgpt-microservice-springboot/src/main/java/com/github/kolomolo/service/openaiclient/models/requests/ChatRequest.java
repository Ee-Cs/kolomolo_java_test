package com.github.kolomolo.service.openaiclient.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The chat request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest implements Serializable {
    private String question;
}
