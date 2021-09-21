package com.example.springcat.storage.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfig {

    private final StorageProperties properties;

    @Autowired
    StorageConfig(StorageProperties properties) {
        this.properties = properties;
    }

    @Bean
    StorageDir storageDir() {
        val uploadPath = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();

        log.info("Initialing upload directory: {}", uploadPath.toString());

        try {
            Files.createDirectories(uploadPath);
            return StorageDir.builder().uploadDir(uploadPath).build();

        } catch (IOException e) {
            throw new UnsupportedOperationException("Could not created the directory, please check the permission");
        }
    }
}
