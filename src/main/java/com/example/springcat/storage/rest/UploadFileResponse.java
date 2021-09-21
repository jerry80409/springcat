package com.example.springcat.storage.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UploadFileResponse {

    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private Long size;
}
