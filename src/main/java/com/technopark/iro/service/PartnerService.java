package com.technopark.iro.service;

import com.technopark.iro.dto.CreatePartnerRequest;
import com.technopark.iro.dto.PartnerResponse;
import com.technopark.iro.dto.UpdatePartnerRequest;
import com.technopark.iro.repository.filter.PartnerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartnerService {

    List<PartnerResponse> getAllPartners();

    PartnerResponse getPartnerById(Long id);

    Page<PartnerResponse> getAllPartnersByPage(Pageable pageable);

    Page<PartnerResponse> getFilteredPartners(PartnerFilter filter, Pageable pageable);

    PartnerResponse createPartner(CreatePartnerRequest request);

    PartnerResponse updatePartner(Long id, UpdatePartnerRequest request);

    void deletePartner(Long id);

}
