package com.technopark.iro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record CreatePartnerRequest(@NotBlank(message = "Country code is required")
                                   String country,

                                   @NotBlank(message = "University name is required")
                                   String university,

                                   @NotNull(message = "QS Ranking is required")
                                   @PositiveOrZero(message = "QS Ranking must be a positive number or zero")
                                   Integer qsRanking,

                                   @NotBlank(message = "Faculties are required")
                                   String faculties,

                                   @NotNull(message = "Date of sign is required")
                                   @PastOrPresent(message = "Date of sign cannot be in the future")
                                   LocalDate dateOfSign,

                                   @NotBlank(message = "Quota is required")
                                   String quota,

                                   @NotBlank(message = "Status is required")
                                   String status,

                                   @NotBlank(message = "Logo URL is required")
                                   String logoUrl
) {
}