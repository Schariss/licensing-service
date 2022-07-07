package com.optimagrowth.license.service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.repository.LicenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LicenseServiceTest {

    @Mock
    private LicenseRepository licenseRepository;
    @Mock
    private MessageSource messageSource;
//    @Mock
//    private ServiceConfig serviceConfig;
    @InjectMocks
    private LicenseService underTest;

    @BeforeEach
    void setUp() {
        // Initialize all the mocks in the current class
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cannotGetLicenseWhenLicenseIsNULL() {
        // when
        String licenseId = "1234";
        String organizationId = "OptimaGrowth";
        when(messageSource.getMessage("license.search.error.message", null, null)).
                thenReturn("Unable to find license with License id %s and Organization id %s");
        // then
        assertThrows(IllegalArgumentException.class, () -> underTest.getLicense(licenseId, organizationId));
        Mockito.verify(licenseRepository).findByOrganizationIdAndLicenseId(organizationId, licenseId);
    }

    @Test
    @Disabled
    void createLicense() {
    }

    @Test
    @Disabled
    void updateLicense() {
    }

    @Test
    @Disabled
    void deleteLicense() {
    }
}