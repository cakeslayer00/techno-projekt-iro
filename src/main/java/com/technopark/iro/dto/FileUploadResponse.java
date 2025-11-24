package com.technopark.iro.dto;

public record FileUploadResponse(String id,
                                 String fileName,
                                 String url,
                                 String fileType,
                                 long size) {
}