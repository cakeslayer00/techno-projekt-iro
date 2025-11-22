package com.technopark.iro.repository;

import com.technopark.iro.model.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
