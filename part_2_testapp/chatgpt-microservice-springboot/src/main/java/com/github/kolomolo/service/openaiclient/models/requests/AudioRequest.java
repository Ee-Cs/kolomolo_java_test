package com.github.kolomolo.service.openaiclient.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * The audio request.
 */
@Data
@AllArgsConstructor
public class AudioRequest implements Serializable {
    private MultipartFile file;
}
