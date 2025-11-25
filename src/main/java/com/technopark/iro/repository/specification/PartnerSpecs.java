package com.technopark.iro.repository.specification;

import com.technopark.iro.model.entity.Partner;
import com.technopark.iro.repository.filter.PartnerFilter;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class PartnerSpecs {

    public static Specification<Partner> filterBy(PartnerFilter partnerfilter) {
        return Specification.allOf(
                hasUniversity(partnerfilter.university()),
                hasCountry(partnerfilter.country()),
                hasStatus(partnerfilter.status()));
    }

    private static Specification<Partner> hasCountry(String country) {
        return (root, query, cb) ->
                country == null || country.isEmpty()
                        ? null
                        : cb.equal(root.get("country"), country);
    }

    private static Specification<Partner> hasUniversity(String university) {
        return (root, query, cb) ->
                university == null || university.isEmpty()
                        ? null
                        : cb.equal(root.get("university"), university);
    }

    private static Specification<Partner> hasStatus(String status) {
        return (root, query, cb) ->
                status == null || status.isEmpty()
                        ? null
                        : cb.equal(root.get("status"), status);
    }

}