package com.technopark.iro.controller;

import com.technopark.iro.dto.CreatePartnerRequest;
import com.technopark.iro.dto.PartnerResponse;
import com.technopark.iro.dto.UpdatePartnerRequest;
import com.technopark.iro.repository.filter.PartnerFilter;
import com.technopark.iro.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "Partners", description = "API for managing business partners")
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping
    @Operation(
            summary = "Get all partners",
            description = "Retrieves a list of all partners without pagination"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    public ResponseEntity<List<PartnerResponse>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @GetMapping("/{partnerId}")
    @Operation(
            summary = "Get partner by ID",
            description = "Retrieves a specific partner by their unique identifier"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Partner found",
                    content = @Content(schema = @Schema(implementation = PartnerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Partner not found",
                    content = @Content
            )
    })
    public ResponseEntity<PartnerResponse> getPartnerById(
            @Parameter(description = "ID of the partner to be retrieved")
            @PathVariable Long partnerId) {
        return ResponseEntity.ok(partnerService.getPartnerById(partnerId));
    }

    @GetMapping("/pages")
    @Operation(
            summary = "Get partners (Paginated)",
            description = "Retrieves a paginated list of partners"
    )
    public ResponseEntity<Page<PartnerResponse>> getAllPartnersByPage(
            @ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(partnerService.getAllPartnersByPage(pageable));
    }

    @GetMapping("/filter")
    @Operation(
            summary = "Filter partners",
            description = "Retrieves a paginated list of partners based on filter criteria"
    )
    public ResponseEntity<Page<PartnerResponse>> getAllFilteredByPage(
            @ParameterObject @ModelAttribute PartnerFilter partnerFilter,
            @ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(partnerService.getFilteredPartners(partnerFilter, pageable));
    }

    @PostMapping
    @Operation(
            summary = "Create a new partner",
            description = "Creates a new partner resource"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Partner created successfully",
                    content = @Content(schema = @Schema(implementation = PartnerResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content)
    })
    public ResponseEntity<PartnerResponse> createPartner(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload with partner upload data",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @Valid @RequestBody CreatePartnerRequest request) {
        PartnerResponse created = partnerService.createPartner(request);
        return ResponseEntity
                .created(URI.create("/api/partners/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a partner",
            description = "Updates an existing partner's details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Partner updated successfully",
                    content = @Content(schema = @Schema(implementation = PartnerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Partner not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<PartnerResponse> updatePartner(
            @Parameter(description = "ID of the partner to update")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload with partner upload data",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @Valid @RequestBody UpdatePartnerRequest request) {
        return ResponseEntity.ok(partnerService.updatePartner(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a partner",
            description = "Permanently removes a partner from the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Partner deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Partner not found", content = @Content)
    })
    public ResponseEntity<Void> deletePartner(
            @Parameter(description = "ID of the partner to delete")
            @PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }

}