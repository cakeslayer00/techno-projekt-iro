package com.technopark.iro.mapper;

import com.technopark.iro.dto.CreateNewsRequest;
import com.technopark.iro.dto.NewsResponse;
import com.technopark.iro.dto.UpdateNewsRequest;
import com.technopark.iro.model.entity.News;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface NewsMapper {

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd")
    NewsResponse toResponse(News news);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    News toEntity(CreateNewsRequest createNewsRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateNewsRequest request, @MappingTarget News partner);

}
