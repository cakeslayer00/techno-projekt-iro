package com.technopark.iro.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String upload(MultipartFile file);

    Resource download(String filename);

}
