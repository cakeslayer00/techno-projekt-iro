package com.technopark.iro.controller;

import com.technopark.iro.dto.CreatePartnerRequest;
import com.technopark.iro.dto.PartnerResponse;
import com.technopark.iro.dto.UpdatePartnerRequest;
import com.technopark.iro.mapper.PartnerMapper;
import com.technopark.iro.model.entity.Partner;
import com.technopark.iro.repository.PartnerRepository;
import com.technopark.iro.repository.filter.PartnerFilter;
import com.technopark.iro.repository.specification.PartnerSpecs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerRepository partnerRepository;

    @GetMapping
    public ResponseEntity<List<PartnerResponse>> getAllPartners() {
        return ResponseEntity.ok(partnerRepository.findAll().stream()
                .map(PartnerMapper.INSTANCE::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerResponse> getPartnerById(@PathVariable Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));
        return ResponseEntity.ok(PartnerMapper.INSTANCE.toResponse(partner));
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<PartnerResponse>> getAllNewsByPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(partnerRepository.findAll(pageable).map(PartnerMapper.INSTANCE::toResponse));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<PartnerResponse>> getAllFilteredByPage(@ModelAttribute PartnerFilter partnerFilter,
                                                                      @PageableDefault Pageable pageable) {
        Specification<Partner> spec = PartnerSpecs.filterBy(partnerFilter);
        return ResponseEntity.ok(partnerRepository.findAll(spec, pageable).map(PartnerMapper.INSTANCE::toResponse));
    }

    @PostMapping
    public ResponseEntity<Void> createPartner(@Valid @RequestBody CreatePartnerRequest createPartnerRequest) {
        Partner partner = new Partner();

        partner.setCountry(createPartnerRequest.country());
        partner.setUniversity(createPartnerRequest.university());
        partner.setQsRanking(createPartnerRequest.qsRanking());
        partner.setFaculties(createPartnerRequest.faculties());
        partner.setDateOfSign(createPartnerRequest.dateOfSign());
        partner.setQuota(createPartnerRequest.quota());
        partner.setStatus(createPartnerRequest.status());
        partner.setLogoUrl(createPartnerRequest.logoUrl());

        partnerRepository.save(partner);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePartner(@PathVariable Long id,
                                              @Valid @RequestBody UpdatePartnerRequest updatePartnerRequest) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));

        partner.setCountry(updatePartnerRequest.country());
        partner.setUniversity(updatePartnerRequest.university());
        partner.setQsRanking(updatePartnerRequest.qsRanking());
        partner.setFaculties(updatePartnerRequest.faculties());
        partner.setDateOfSign(updatePartnerRequest.dateOfSign());
        partner.setQuota(updatePartnerRequest.quota());
        partner.setStatus(updatePartnerRequest.status());
        partner.setLogoUrl(updatePartnerRequest.logoUrl());

        partnerRepository.save(partner);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        if (!partnerRepository.existsById(id)) {
            throw new RuntimeException("Partner not found with id: " + id);
        }

        partnerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
