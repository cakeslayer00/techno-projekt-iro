package com.technopark.iro.controller;

import com.technopark.iro.dto.CreatePartnerRequest;
import com.technopark.iro.dto.UpdatePartnerRequest;
import com.technopark.iro.model.entity.Partner;
import com.technopark.iro.repository.PartnerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnersController {

    private final PartnerRepository partnerRepository;

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

    @GetMapping
    public ResponseEntity<? extends Iterable<Partner>> getAllPartners() {
        return ResponseEntity.ok(partnerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));
        return ResponseEntity.ok(partner);
    }

}
