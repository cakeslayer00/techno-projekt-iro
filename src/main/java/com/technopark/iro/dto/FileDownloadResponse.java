package com.technopark.iro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.io.Resource;

@Schema(description = "Container for file download stream and metadata")
public record FileDownloadResponse(@Schema(hidden = true)
                                   @JsonIgnore
                                   Resource resource,

                                   @Schema(description = "MIME type of the file", example = "image/jpeg")
                                   @JsonProperty("content_type")
                                   String contentType,

                                   @Schema(description = "Size of the file in bytes", example = "1048576")
                                   long size) {
}