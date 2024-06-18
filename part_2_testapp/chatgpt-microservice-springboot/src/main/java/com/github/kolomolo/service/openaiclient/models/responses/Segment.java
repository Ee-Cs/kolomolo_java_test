package com.github.kolomolo.service.openaiclient.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The segment.
 */
@Data
@AllArgsConstructor
public class Segment implements Serializable {
    private int id;
    private int seek;
    private double start;
    private double end;
    private String text;
    private List<Integer> tokens;
    private double temperature;
    private double avgLogprob;
    private double compressionRatio;
    private double noSpeechProb;
}
