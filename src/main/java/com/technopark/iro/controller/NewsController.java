package com.technopark.iro.controller;

import com.technopark.iro.dto.CreateNewsRequest;
import com.technopark.iro.dto.NewsResponse;
import com.technopark.iro.dto.UpdateNewsRequest;
import com.technopark.iro.repository.filter.NewsFilter;
import com.technopark.iro.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Tag(name = "News", description = "API for managing news articles")
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    @Operation(summary = "Get all news", description = "Retrieves a list of all news articles without pagination")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved list",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsResponse.class)))
    )
    public ResponseEntity<List<NewsResponse>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get news by ID", description = "Retrieves a specific news article by its unique identifier")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "News found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NewsResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "News not found")
    })
    public ResponseEntity<NewsResponse> getNewsById(
            @Parameter(description = "ID of the news article to be retrieved")
            @PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping("/pages")
    @Operation(summary = "Get news (Paginated)", description = "Retrieves a paginated list of news articles")
    public ResponseEntity<Page<NewsResponse>> getAllNewsByPage(@ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(newsService.getAllNewsByPage(pageable));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter news", description = "Retrieves a paginated list of news articles based on filter criteria")
    public ResponseEntity<Page<NewsResponse>> getAllFilteredByPage(@ParameterObject @ModelAttribute NewsFilter newsFilter,
                                                                   @ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(newsService.getFilteredNews(newsFilter, pageable));
    }

    @PostMapping
    @Operation(summary = "Create a news article", description = "Creates a new news article resource")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "News created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NewsResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<NewsResponse> createNews(@Valid @RequestBody CreateNewsRequest createNewsRequest) {
        NewsResponse news = newsService.createNews(createNewsRequest);
        return ResponseEntity
                .created(URI.create("/api/news/" + news.id()))
                .body(news);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a news article", description = "Updates an existing news article's details")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "News updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NewsResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "News not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<NewsResponse> updateNews(@Parameter(description = "ID of the news article to update")
                                                   @PathVariable Long id,
                                                   @Valid @RequestBody UpdateNewsRequest updateNewsRequest) {
        return ResponseEntity.ok(newsService.updateNews(id, updateNewsRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a news article", description = "Permanently removes a news article from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "News deleted successfully"),
            @ApiResponse(responseCode = "404", description = "News not found")
    })
    public ResponseEntity<Void> deleteNews(
            @Parameter(description = "ID of the news article to delete")
            @PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

}