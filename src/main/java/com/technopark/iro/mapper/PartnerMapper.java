package com.technopark.iro.mapper;

import com.technopark.iro.dto.PartnerResponse;
import com.technopark.iro.model.entity.Partner;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PartnerMapper {

    PartnerMapper INSTANCE = Mappers.getMapper(PartnerMapper.class);

    PartnerResponse toResponse(Partner partner);

}
