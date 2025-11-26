package com.technopark.iro.repository.specification;

import com.technopark.iro.model.entity.News;
import com.technopark.iro.repository.filter.NewsFilter;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class NewsSpecs {

    private static final String WILDCARD = "%";

    public static Specification<News> filterBy(NewsFilter newsFilter) {
        return Specification.allOf(
                hasTitle(newsFilter.title()),
                hasContent(newsFilter.content()),
                hasCreatedAt(newsFilter.createdAt()),
                hasStatus(newsFilter.status()));
    }

    private static Specification<News> hasTitle(String title) {
        return (root, query, cb) ->
                title == null || title.isBlank()
                        ? null
                        : cb.like(root.get("title"), WILDCARD + title + WILDCARD);
    }

    private static Specification<News> hasContent(String content) {
        return (root, query, cb) ->
                content == null || content.isBlank()
                        ? null
                        : cb.like(root.get("content"), WILDCARD + content + WILDCARD);
    }

    private static Specification<News> hasCreatedAt(LocalDate createdAt) {
        return (root, query, cb) -> {
            if (createdAt == null) {
                return null;
            }

            LocalDateTime startOfDay = createdAt.atStartOfDay();
            LocalDateTime endOfDay = createdAt.plusDays(1).atStartOfDay();

            return cb.between(root.get("createdAt"), startOfDay, endOfDay);
        };
    }

    private static Specification<News> hasStatus(String status) {
        return (root, query, cb) ->
                status == null || status.isBlank()
                        ? null
                        : cb.equal(root.get("status"), status);
    }

}

