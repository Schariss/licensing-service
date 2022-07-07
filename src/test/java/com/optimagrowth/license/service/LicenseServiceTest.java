package com.optimagrowth.license.service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.repository.LicenseRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Use parameters for initialization of mocks that you use only in that specific test method
// Initializes mocks annotated with @Mock, so that explicit usage of MockitoAnnotations#initMocks(Object) is not necessary
@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LicenseServiceTest {

    @Mock
    private LicenseRepository licenseRepository;
    @Mock
    private MessageSource messageSource;
    @Mock
    private ServiceConfig serviceConfig;
    @InjectMocks
    private LicenseService underTest;

    @BeforeEach
    void setUp() {
        // Initialize all the mocks in the current class
        //MockitoAnnotations.openMocks(this);
        when(serviceConfig.getProperty()).thenReturn("I AM THE DEFAULT");
    }

    @Test
    void canGetLicense() {
        // when
        String licenseId = "1234";
        String organizationId = "OptimaGrowth";
        // Mockito throws an UnsupportedStubbingException when an initialized mock is not called by one of the test methods
        // during execution. We can avoid this strict stub checking by using this method when initializing the mocks.
        Mockito.lenient().when(serviceConfig.getProperty()).thenReturn("I AM Adnane");
        when(messageSource.getMessage("license.search.error.message", null, null)).
                thenReturn("Unable to find license");
        // then
        assertThrows(IllegalArgumentException.class, () -> underTest.getLicense(licenseId, organizationId));
        Mockito.verify(licenseRepository).findByOrganizationIdAndLicenseId(organizationId, licenseId);
    }

    @Test
    void canCreateLicense() {
        // given
        License license = new License();
        // when
        underTest.createLicense(license, "OptimaGrowth");
        // then
        ArgumentCaptor<License> licenseArgumentCaptor = ArgumentCaptor.forClass(License.class);
        verify(licenseRepository).save(licenseArgumentCaptor.capture());
        License capturedLicense = licenseArgumentCaptor.getValue();
        assertThat(capturedLicense).isEqualTo(license);
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