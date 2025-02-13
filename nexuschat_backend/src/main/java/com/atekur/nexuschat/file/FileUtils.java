package com.atekur.nexuschat.file;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {

    private FileUtils() {
    }

    public static byte[] readFileFromLocation(String fileurl){
        if (StringUtils.isBlank(fileurl)) {
            return new byte[0];
        }
        try {
            Path file = new File(fileurl).toPath();
            return Files.readAllBytes(file);
        } catch (IOException e) {
            log.error("Failed to read file from location: {}", fileurl, e);
        }
        return new byte[0];
    }
}
