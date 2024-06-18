package com.github.kolomolo.service.openaiclient;

import java.nio.file.Path;

/**
 * The constants.
 */
public class Constants {
    /**
     * secret file
     */
    public static final Path SECRET_FILE = Path.of("..\\..\\..\\apiKey.txt");
    /**
     * tmp dir key
     */
    public static final String TMP_DIR_KEY = "java.io.tmpdir";
    /**
     * prefix
     */
    public static final String PREFIX = "sample";
    /**
     * suffix
     */
    public static final String SUFFIX = ".mp3";

    /**
     * The constructor
     */
    public Constants() {
    }
}
