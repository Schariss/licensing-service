package com.optimagrowth.license.repository;

import com.optimagrowth.license.model.License;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// @DataJpaTest is used to test JPA repositories
//  The annotation disables full autoconfiguration and applies only configuration relevant to JPA tests
// By default, tests annotated with @DataJpaTest use an embedded in-memory database
@DataJpaTest
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LicenseRepositoryTest {

    @Autowired
    private LicenseRepository underTest;

    @Test
    @Disabled
    void itShouldSelectsLicencesByOrganizationId() {
    }

    @Test
    void itShouldSelectsLicenceByOrganizationIdAndLicenseId() {
        // given
        String licenseId = "1234";
        String organizationId = "OptimaGrowth";
        License license = new License(licenseId, "Good License", organizationId,
                "Software License", "TypeA", "No comment");
        underTest.save(license);
        // when
        License result = underTest.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        // then
        assertThat(result).isEqualTo(license);
    }

    @Test
    void itShouldReturnsNULLWhenLicenceDoesNotExists() {
        // given
        String licenseId = "1234";
        String organizationId = "OptimaGrowth";
        // when
        License result = underTest.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        // then
        assertThat(result).isEqualTo(null);
    }
}