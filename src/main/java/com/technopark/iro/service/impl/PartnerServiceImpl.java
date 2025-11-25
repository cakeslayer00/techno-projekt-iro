package com.technopark.iro.service.impl;

import com.technopark.iro.dto.CreatePartnerRequest;
import com.technopark.iro.dto.PartnerResponse;
import com.technopark.iro.dto.UpdatePartnerRequest;
import com.technopark.iro.exception.PartnerNotFoundException;
import com.technopark.iro.mapper.PartnerMapper;
import com.technopark.iro.model.entity.Partner;
import com.technopark.iro.repository.PartnerRepository;
import com.technopark.iro.repository.filter.PartnerFilter;
import com.technopark.iro.repository.specification.PartnerSpecs;
import com.technopark.iro.service.PartnerService;
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
public class PartnerServiceImpl implements PartnerService {

    private static final String ERR_PARTNER_NOT_FOUND = "Partner not found with id: %s";

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    @Override
    public List<PartnerResponse> getAllPartners() {
        log.debug("Fetching all partners");
        return partnerRepository.findAll().stream()
                .map(partnerMapper::toResponse)
                .toList();
    }

    @Override
    public PartnerResponse getPartnerById(Long id) {
        log.debug("Fetching partner with id: {}", id);
        return partnerRepository.findById(id)
                .map(partnerMapper::toResponse)
                .orElseThrow(() -> new PartnerNotFoundException(ERR_PARTNER_NOT_FOUND.formatted(id)));
    }

    @Override
    public Page<PartnerResponse> getAllPartnersByPage(Pageable pageable) {
        log.debug("Fetching partners page: {}", pageable);
        return partnerRepository.findAll(pageable)
                .map(partnerMapper::toResponse);
    }

    @Override
    public Page<PartnerResponse> getFilteredPartners(PartnerFilter filter,
                                                     Pageable pageable) {
        log.debug("Fetching filtered partners with filter: {} and pageable: {}", filter, pageable);
        Specification<Partner> spec = PartnerSpecs.filterBy(filter);
        return partnerRepository.findAll(spec, pageable)
                .map(partnerMapper::toResponse);
    }

    @Override
    @Transactional
    public PartnerResponse createPartner(CreatePartnerRequest request) {
        log.info("Creating new partner: {}", request.university());

        Partner partner = partnerMapper.toEntity(request);
        Partner saved = partnerRepository.save(partner);

        log.info("Partner created with id: {}", saved.getId());
        return partnerMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PartnerResponse updatePartner(Long id, UpdatePartnerRequest request) {
        log.info("Updating partner with id: {}", id);

        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new PartnerNotFoundException(ERR_PARTNER_NOT_FOUND.formatted(id)));

        partnerMapper.updateEntity(request, partner);
        Partner updated = partnerRepository.save(partner);

        log.info("Partner updated with id: {}", id);
        return partnerMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deletePartner(Long id) {
        log.info("Deleting partner with id: {}", id);

        if (!partnerRepository.existsById(id)) {
            throw new PartnerNotFoundException(ERR_PARTNER_NOT_FOUND.formatted(id));
        }

        partnerRepository.deleteById(id);
        log.info("Partner deleted with id: {}", id);
    }
}