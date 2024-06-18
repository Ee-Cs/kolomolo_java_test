package com.github.kolomolo.service.openaiclient.models.responses;

import com.github.kolomolo.service.openaiclient.models.requests.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * The Choice.
 */
@Data
@AllArgsConstructor
public class Choice implements Serializable {
    private Integer index;
    private Message message;
    private String finishReason;
}
