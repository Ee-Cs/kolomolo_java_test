package com.github.kolomolo.service.openaiclient.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * The Usage.
 */
@Data
public class Usage implements Serializable {
    private String promptTokens;
    private String completionTokens;
    private String totalTokens;
}
