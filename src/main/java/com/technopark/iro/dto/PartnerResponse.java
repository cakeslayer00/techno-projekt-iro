package com.technopark.iro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Partner response")
public record PartnerResponse(@Schema(description = "Partner ID", example = "1")
                              Long id,

                              @Schema(description = "Country name", example = "United States")
                              String country,

                              @Schema(description = "University name", example = "MIT")
                              String university,

                              @Schema(description = "QS Ranking", example = "1")
                              @JsonProperty("qs_ranking")
                              Integer qsRanking,

                              @Schema(description = "Faculties", example = "Engineering, Computer Science")
                              String faculties,

                              @Schema(description = "Date of agreement signing", example = "2024-01-15")
                              @JsonProperty("date_of_sign")
                              String dateOfSign,

                              @Schema(description = "Student quota", example = "10")
                              String quota,

                              @Schema(description = "Partnership status", example = "ACTIVE")
                              String status,

                              @Schema(description = "Logo URL", example = "https://storage.com/logo.png")
                              @JsonProperty("logo_url")
                              String logoUrl) {
}