package com.technopark.iro.service;

import com.technopark.iro.dto.CreateNewsRequest;
import com.technopark.iro.dto.NewsResponse;
import com.technopark.iro.dto.UpdateNewsRequest;
import com.technopark.iro.repository.filter.NewsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {

    List<NewsResponse> getAllNews();

    NewsResponse getNewsById(Long id);

    Page<NewsResponse> getAllNewsByPage(Pageable pageable);

    Page<NewsResponse> getFilteredNews(NewsFilter filter, Pageable pageable);

    NewsResponse createNews(CreateNewsRequest request);

    NewsResponse updateNews(Long id, UpdateNewsRequest request);

    void deleteNews(Long id);

}
