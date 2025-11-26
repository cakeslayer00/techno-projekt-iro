package com.technopark.iro.repository.filter;

import java.time.LocalDate;

public record NewsFilter(String title,
                         String content,
                         LocalDate createdAt,
                         String status) {
}
