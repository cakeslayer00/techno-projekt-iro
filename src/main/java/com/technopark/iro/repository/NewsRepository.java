package com.technopark.iro.repository;

import com.technopark.iro.model.entity.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
}
