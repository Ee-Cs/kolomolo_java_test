package com.github.kolomolo.service.openaiclient.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * The Usage.
 */
@Data
@AllArgsConstructor
public class Usage implements Serializable {
    private String promptTokens;
    private String completionTokens;
    private String totalTokens;
}
