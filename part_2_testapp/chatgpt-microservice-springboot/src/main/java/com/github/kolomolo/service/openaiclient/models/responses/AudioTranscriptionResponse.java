package com.github.kolomolo.service.openaiclient.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The audio transcription response.
 */
@Data
@AllArgsConstructor
public class AudioTranscriptionResponse implements Serializable {
    private String task;
    private String language;
    private double duration;
    private String text;
    private List<Segment> segments;
}