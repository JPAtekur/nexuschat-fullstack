package com.atekur.nexuschat.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${application.file.uploads.media-output-path}")
    private String fileUploadPath;

    public String saveFile(@NonNull MultipartFile sourceFile, @NonNull String senderId) {
        final String fileUploadSubPath = "users" + separator + senderId;

        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile sourceFile, @NonNull String fileUploadSubPath) {

        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.error("Failed to create folder: {}", targetFolder.getAbsolutePath());
                return null;
            }
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        final String targetFilePath = fileUploadPath + separator + currentTimeMillis() + fileExtension;
        Path targetPath = Path.of(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved successfully: {}", targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("Failed to save file: {}", targetFilePath, e);
        }
        return null;
    }

    private String getFileExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isEmpty()) {
            return "";
        }
        int lastIndexOf = originalFilename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return originalFilename.substring(lastIndexOf).toLowerCase();
    }
}
