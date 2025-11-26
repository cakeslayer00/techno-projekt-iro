package com.technopark.iro.service.impl;

import com.technopark.iro.dto.CreateNewsRequest;
import com.technopark.iro.dto.NewsResponse;
import com.technopark.iro.dto.UpdateNewsRequest;
import com.technopark.iro.exception.NewsNotFoundException;
import com.technopark.iro.exception.PartnerNotFoundException;
import com.technopark.iro.mapper.NewsMapper;
import com.technopark.iro.model.entity.News;
import com.technopark.iro.repository.NewsRepository;
import com.technopark.iro.repository.filter.NewsFilter;
import com.technopark.iro.repository.specification.NewsSpecs;
import com.technopark.iro.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private static final String ERR_NEWS_NOT_FOUND = "News not found with id: %s";

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public List<NewsResponse> getAllNews() {
        log.debug("Fetching all news");
        return newsRepository.findAll()
                .stream()
                .map(newsMapper::toResponse)
                .toList();
    }

    @Override
    public NewsResponse getNewsById(Long id) {
        log.debug("Fetching news with id: {}", id);
        return newsRepository.findById(id)
                .map(newsMapper::toResponse)
                .orElseThrow(() -> new NewsNotFoundException(ERR_NEWS_NOT_FOUND.formatted(id)));
    }

    @Override
    public Page<NewsResponse> getAllNewsByPage(Pageable pageable) {
        log.debug("Fetching news page: {}", pageable);
        return newsRepository.findAll(pageable)
                .map(newsMapper::toResponse);
    }

    @Override
    public Page<NewsResponse> getFilteredNews(NewsFilter filter, Pageable pageable) {
        log.debug("Fetching filtered news with filter: {} and pageable: {}", filter, pageable);
        Specification<News> spec = NewsSpecs.filterBy(filter);
        return newsRepository.findAll(spec, pageable)
                .map(newsMapper::toResponse);
    }

    @Override
    @Transactional
    public NewsResponse createNews(CreateNewsRequest request) {
        log.info("Creating new news with title: {}", request.title());

        News news = newsMapper.toEntity(request);
        News saved = newsRepository.save(news);

        log.info("News created with id: {}", saved.getId());
        return newsMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public NewsResponse updateNews(Long id, UpdateNewsRequest request) {
        log.info("Updating news with id: {}", id);

        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(ERR_NEWS_NOT_FOUND.formatted(id)));

        newsMapper.updateEntity(request, news);
        News updated = newsRepository.save(news);

        log.info("News updated with id: {}", id);
        return newsMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteNews(Long id) {
        log.info("Deleting news with id: {}", id);

        if (!newsRepository.existsById(id)) {
            throw new PartnerNotFoundException(ERR_NEWS_NOT_FOUND.formatted(id));
        }

        newsRepository.deleteById(id);
        log.info("News deleted with id: {}", id);
    }

}
