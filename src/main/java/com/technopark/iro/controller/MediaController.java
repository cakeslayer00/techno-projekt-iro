package com.technopark.iro.controller;

import com.technopark.iro.dto.FileDownloadResponse;
import com.technopark.iro.dto.FileUploadResponse;
import com.technopark.iro.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
@Tag(name = "Media", description = "API for file upload and download operations")
public class MediaController {

    private final StorageService storageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload a file",
            description = "Uploads a file to the server and returns file metadata including download URL"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "File uploaded successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FileUploadResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid file or file upload failed"),
            @ApiResponse(responseCode = "413", description = "File size exceeds maximum limit")
    })
    public ResponseEntity<FileUploadResponse> uploadFile(
            @Parameter(
                    description = "File to be uploaded",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
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

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/download/{filename:.+}")
    @Operation(
            summary = "Download a file",
            description = "Downloads a file from the server by its stored filename"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "File downloaded successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "File not found"
            )
    })
    public ResponseEntity<Resource> downloadFile(
            @Parameter(
                    description = "Name of the file to download",
                    required = true,
                    example = "image.jpg"
            )
            @PathVariable String filename) {
        FileDownloadResponse download = storageService.download(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(download.contentType()))
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(download.size()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(download.resource());
    }

}