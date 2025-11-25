package com.technopark.iro.mapper;

import com.technopark.iro.dto.NewsResponse;
import com.technopark.iro.model.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    NewsResponse toResponse(News news);

}
