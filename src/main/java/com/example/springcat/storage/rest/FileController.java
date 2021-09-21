package com.example.springcat.storage.rest;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import com.example.springcat.storage.config.StorageDir;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;
    private final StorageDir storageDir;

    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> download(@PathVariable String fileName) {
        final Resource resource = storageService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(CONTENT_DISPOSITION, "attachment; filename='" + resource.getFilename() + "'")
            .body(resource);
    }

    @PostMapping("/upload")
    ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        val fileName = storageService.uploadFile(file);
        val downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("download/")
            .path(fileName)
            .toUriString();

        return ResponseEntity.ok(UploadFileResponse.builder()
            .fileName(fileName)
            .fileDownloadUri(downloadUri)
            .fileType(file.getContentType())
            .size(file.getSize())
            .build());
    }
}
