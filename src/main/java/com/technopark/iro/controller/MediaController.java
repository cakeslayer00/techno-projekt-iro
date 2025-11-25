package com.technopark.iro.controller;

import com.technopark.iro.dto.FileUploadResponse;
import com.technopark.iro.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private static final String DOWNLOAD_CONTENT_TYPE = "application/octet-stream";

    private final StorageService storageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file) {
        String storedFileName = storageService.upload(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/media/download/")
                .path(storedFileName)
                .toUriString();

        FileUploadResponse response = new FileUploadResponse(
                storedFileName,
                file.getOriginalFilename(),
                fileDownloadUri,
                file.getContentType(),
                file.getSize()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = storageService.download(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(DOWNLOAD_CONTENT_TYPE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}
