package com.example.springcat.storage.rest;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;

    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> hello(@PathVariable String fileName) {
        final Resource resource = storageService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(CONTENT_DISPOSITION, "attachment; filename='" + resource.getFilename() + "'")
            .body(resource);
    }
}
