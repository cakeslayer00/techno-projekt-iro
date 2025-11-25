package com.technopark.iro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Request to create a new partner")
public record CreatePartnerRequest(@NotBlank(message = "Country is required")
                                   @Size(max = 100, message = "Country name must not exceed 100 characters")
                                   @Schema(description = "Country name", example = "United States", requiredMode = Schema.RequiredMode.REQUIRED)
                                   String country,

                                   @NotBlank(message = "University is required")
                                   @Size(max = 255, message = "University name must not exceed 255 characters")
                                   @Schema(description = "University name", example = "MIT", requiredMode = Schema.RequiredMode.REQUIRED)
                                   String university,

                                   @Min(value = 1, message = "QS ranking must be at least 1")
                                   @Schema(description = "QS World University Ranking", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
                                   @JsonProperty("qs_ranking")
                                   Integer qsRanking,

                                   @NotBlank(message = "Faculties information is required")
                                   @Schema(description = "List of faculties", example = "Engineering, Computer Science", requiredMode = Schema.RequiredMode.REQUIRED)
                                   String faculties,

                                   @NotNull(message = "Date of signing is required")
                                   @PastOrPresent(message = "Date of signing must not be in the future")
                                   @Schema(description = "Date when the partnership agreement was signed",
                                           example = "2024-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
                                   @JsonProperty("date_of_sign")
                                   LocalDate dateOfSign,

                                   @NotBlank(message = "Quota is required")
                                   @Size(max = 50, message = "Quota must not exceed 50 characters")
                                   @Schema(description = "Student exchange quota", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
                                   String quota,

                                   @NotBlank(message = "Status is required")
                                   /*TODO: Idea for possible implementation: @Pattern(regexp = "ACTIVE|INACTIVE|PENDING",
                                           message = "Status must be ACTIVE, INACTIVE, or PENDING")*/
                                   @Schema(description = "Partnership status",
                                           example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
                                   String status,

                                   @NotBlank(message = "Logo URL is required")
                                   @Size(max = 500, message = "Logo URL must not exceed 500 characters")
                                   @Schema(description = "URL to partner university logo lying in MinIO storage",
                                           example = "https://hostedstorage.com/logo.png", requiredMode = Schema.RequiredMode.REQUIRED)
                                   @JsonProperty("logo_url")
                                   String logoUrl) {
}