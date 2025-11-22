package com.technopark.iro.controller;

import com.technopark.iro.dto.CreateNewsRequest;
import com.technopark.iro.dto.UpdateNewsRequest;
import com.technopark.iro.model.entity.News;
import com.technopark.iro.repository.NewsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNews(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNewsRequest updateNewsRequest) {

        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));

        news.setTitle(updateNewsRequest.title());
        news.setContent(updateNewsRequest.content());
        news.setImageUrl(updateNewsRequest.imageUrl());

        if (updateNewsRequest.status() != null) {
            news.setStatus(updateNewsRequest.status());
        }

        newsRepository.save(news);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        if (!newsRepository.existsById(id)) {
            throw new RuntimeException("News not found with id: " + id);
        }

        newsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<? extends Iterable<News>> getAllNews() {
        return ResponseEntity.ok(newsRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
        return ResponseEntity.ok(news);
    }

}