package com.technopark.iro.dto;

public record PartnerResponse(String country,
                              String university,
                              Integer qsRanking,
                              String faculties,
                              String dateOfSign,
                              String quota,
                              String status,
                              String logoUrl) {
}
