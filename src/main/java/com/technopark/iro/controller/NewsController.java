package com.technopark.iro.controller;

import com.technopark.iro.dto.CreateNewsRequest;
import com.technopark.iro.model.entity.News;
import com.technopark.iro.repository.NewsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsRepository newsRepository;

    @PostMapping
    public ResponseEntity<Void> createNews(@Valid @RequestBody CreateNewsRequest createNewsRequest) {
        News news = new News();
        news.setTitle(createNewsRequest.title());
        news.setContent(createNewsRequest.content());
        news.setImageUrl(createNewsRequest.imageUrl());

        if (createNewsRequest.status() != null) {
            news.setStatus(createNewsRequest.status());
        }

        newsRepository.save(news);
        return ResponseEntity.ok().build();
    }

}
