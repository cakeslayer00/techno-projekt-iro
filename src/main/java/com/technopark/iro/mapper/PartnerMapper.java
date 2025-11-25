package com.technopark.iro.mapper;

import com.technopark.iro.dto.CreatePartnerRequest;
import com.technopark.iro.dto.PartnerResponse;
import com.technopark.iro.dto.UpdatePartnerRequest;
import com.technopark.iro.model.entity.Partner;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface PartnerMapper {

    @Mapping(target = "dateOfSign", source = "dateOfSign", dateFormat = "yyyy-MM-dd")
    PartnerResponse toResponse(Partner partner);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Partner toEntity(CreatePartnerRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdatePartnerRequest request, @MappingTarget Partner partner);
}