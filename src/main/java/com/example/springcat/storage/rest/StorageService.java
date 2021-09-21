package com.example.springcat.storage.rest;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import com.example.springcat.storage.config.StorageDir;
import com.example.springcat.web.exception.NotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
class StorageService {

    private final StorageDir storageDir;



    /**
     * store file
     *
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file) {
        val fileName = Optional.ofNullable(file.getOriginalFilename())
            .map(StringUtils::cleanPath)
            .orElse("");

        if (fileName.isBlank() || fileName.contains("..")) {
            throw new UnsupportedOperationException("No file name or file name illegal");
        }

        val targetLocation = storageDir.getUploadDir().resolve(fileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, REPLACE_EXISTING);
            return fileName;

        } catch (IOException e) {
            log.error("Could not store file", e);
            throw new UnsupportedOperationException("Could not store file, cause: " + e.getMessage());
        }
    }

    /**
     * download file
     *
     * @param fileName
     * @return
     */
    public Resource loadFileAsResource(String fileName) {
        val fileLocation = storageDir.getUploadDir().resolve(fileName).normalize();

        try {
            val resource = new UrlResource(fileLocation.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            log.error("File not found", e);
            throw new UnsupportedOperationException("File not found: " + fileName);
        }
    }
}
