package com.technopark.iro.service;

import com.technopark.iro.dto.FileDownloadResponse;
import com.technopark.iro.dto.FileUploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String upload(MultipartFile file);

    FileDownloadResponse download(String filename);

}
