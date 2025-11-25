package com.technopark.iro.dto;

import com.technopark.iro.model.NewsStatus;

import java.time.LocalDate;

public record NewsResponse(String title,
                           String content,
                           LocalDate createdAt,
                           String imageUrl,
                           NewsStatus status) {
}
