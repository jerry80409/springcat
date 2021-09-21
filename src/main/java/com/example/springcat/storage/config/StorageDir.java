package com.example.springcat.storage.config;

import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class StorageDir {

    /**
     * 檔案上傳資料夾
     */
    Path uploadDir;
}
