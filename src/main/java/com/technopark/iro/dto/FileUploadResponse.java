package com.technopark.iro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response containing uploaded file metadata and download information")
public record FileUploadResponse(
        @Schema(
                description = "Unique identifier of the stored file",
                example = "a3d5e7f9-1234-5678-90ab-cdef12345678"
        )
        String id,

        @Schema(
                description = "Original filename as provided during upload",
                example = "image.jpg"
        )
        String fileName,

        @Schema(
                description = "URL to download the file",
                example = "http://localhost:8080/api/media/download/a3d5e7f9-1234-5678-90ab-cdef12345678.jpg"
        )
        String url,

        @Schema(
                description = "MIME type of the uploaded file",
                example = "image/jpeg"
        )
        @JsonProperty("file_type")
        String fileType,

        @Schema(
                description = "File size in bytes",
                example = "2048576"
        )
        long size) {
}