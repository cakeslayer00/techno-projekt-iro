package com.technopark.iro.service.impl;

import com.technopark.iro.config.properties.MinioProperties;
import com.technopark.iro.dto.FileDownloadResponse;
import com.technopark.iro.exception.*;
import com.technopark.iro.service.StorageService;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioStorageService implements StorageService {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp"
    );

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private static final String ERR_FILE_EMPTY = "File cannot be empty";
    private static final String ERR_FILE_SIZE_EXCEEDED = "File size exceeds maximum allowed size of %d MB";
    private static final String ERR_INVALID_FILE_TYPE = "Invalid file type. Allowed types: %s";
    private static final String ERR_FILENAME_EMPTY = "Filename cannot be empty";
    private static final String ERR_FILENAME_INVALID = "Invalid filename";
    private static final String ERR_UPLOAD_FAILED = "Failed to upload file to storage";
    private static final String ERR_DOWNLOAD_FAILED = "Failed to retrieve file from storage: %s";
    private static final String ERR_MINIO_UPLOAD = "MinIO upload failed";

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

    @Override
    public String upload(MultipartFile file) {
        validateFile(file);

        String filename = generateUniqueFilename(file);

        try (InputStream inputStream = file.getInputStream()) {
            uploadToMinio(filename, inputStream, file.getSize(), file.getContentType());
            log.info("Successfully uploaded file: {}", filename);
            return filename;
        } catch (Exception e) {
            log.error("Failed to upload file: {}", filename, e);
            throw new FileStorageException(ERR_UPLOAD_FAILED, e);
        }
    }

    @Override
    public FileDownloadResponse download(String filename) {
        validateFilename(filename);

        try {
            StatObjectResponse sor = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(filename)
                            .build());

            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(filename)
                            .build());

            log.info("Successfully retrieved file: {}", filename);
            return new FileDownloadResponse(new InputStreamResource(stream), sor.contentType(), sor.size());
        } catch (ErrorResponseException e) {
            log.error("Failed to find file: {}", filename, e);
            throw new FileNotFoundException("File not found");
        }  catch (Exception e) {
            log.error("Failed to download file: {}", filename, e);
            throw new FileStorageException(String.format(ERR_DOWNLOAD_FAILED, filename), e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new EmptyFileException(ERR_FILE_EMPTY);
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new MaxUploadSizeExceededException(ERR_FILE_SIZE_EXCEEDED.formatted(MAX_FILE_SIZE / (1024 * 1024)));
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new InvalidContentTypeException(ERR_INVALID_FILE_TYPE.formatted(String.join(", ", ALLOWED_CONTENT_TYPES)));
        }
    }

    private void validateFilename(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new InvalidFileException(ERR_FILENAME_EMPTY);
        }

        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            throw new InvalidFileException(ERR_FILENAME_INVALID);
        }
    }

    private String generateUniqueFilename(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String sanitizedExtension = extension != null ? extension.toLowerCase() : "jpg";
        return UUID.randomUUID() + "." + sanitizedExtension;
    }

    private void uploadToMinio(String filename,
                               InputStream inputStream,
                               long size,
                               String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(filename)
                            .stream(inputStream, size, -1)
                            .contentType(contentType)
                            .build());
        } catch (Exception e) {
            throw new FileStorageException(ERR_MINIO_UPLOAD, e);
        }
    }

}