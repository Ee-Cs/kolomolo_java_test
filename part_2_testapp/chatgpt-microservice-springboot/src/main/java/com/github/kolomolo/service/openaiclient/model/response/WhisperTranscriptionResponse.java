package com.github.kolomolo.service.openaiclient.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * The Whisper Transcription Response.
 */
@Data
public class WhisperTranscriptionResponse implements Serializable {
    private String text;
}
