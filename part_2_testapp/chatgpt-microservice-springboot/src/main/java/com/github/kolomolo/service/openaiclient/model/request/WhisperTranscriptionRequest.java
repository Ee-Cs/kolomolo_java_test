package com.github.kolomolo.service.openaiclient.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * The Whisper Transcription Request.
 */
@Data
public class WhisperTranscriptionRequest implements Serializable {
    private String model;
    private MultipartFile file;
}
