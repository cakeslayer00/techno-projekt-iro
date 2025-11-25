package com.technopark.iro.controller;

import com.technopark.iro.dto.CreatePartnerRequest;
import com.technopark.iro.dto.PartnerResponse;
import com.technopark.iro.dto.UpdatePartnerRequest;
import com.technopark.iro.repository.PartnerRepository;
import com.technopark.iro.repository.filter.PartnerFilter;
import com.technopark.iro.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping
    public ResponseEntity<List<PartnerResponse>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @GetMapping("/{partnerId}")
    public ResponseEntity<PartnerResponse> getPartnerById(@PathVariable Long partnerId) {
        return ResponseEntity.ok(partnerService.getPartnerById(partnerId));
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<PartnerResponse>> getAllPartnersByPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(partnerService.getAllPartnersByPage(pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<PartnerResponse>> getAllFilteredByPage(@ModelAttribute PartnerFilter partnerFilter,
                                                                      @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(partnerService.getFilteredPartners(partnerFilter, pageable));
    }

    @PostMapping
    public ResponseEntity<PartnerResponse> createPartner(
            @Valid @RequestBody CreatePartnerRequest request) {
        PartnerResponse created = partnerService.createPartner(request);
        return ResponseEntity
                .created(URI.create("/api/partners/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartnerResponse> updatePartner(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePartnerRequest request) {
        return ResponseEntity.ok(partnerService.updatePartner(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }

}
