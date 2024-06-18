package com.github.kolomolo.service.openaiclient.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * The Transcription Request.
 */
@Data
@AllArgsConstructor//[KP]???
@NoArgsConstructor//[KP]???
@Builder//[KP]???
public class TranscriptionRequest implements Serializable {
    private MultipartFile file;
}
