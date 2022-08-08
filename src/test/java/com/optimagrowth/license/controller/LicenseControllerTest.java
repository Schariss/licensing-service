package com.optimagrowth.license.controller;

import com.optimagrowth.license.exception.ResourceNotFoundException;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest does not scan beans for our services, so we have to provide a bean
// for anything that the controller depends on
@WebMvcTest(LicenseController.class)
class LicenseControllerTest {

    private LicenseRequestBuilder requestBuilder;
    @MockBean
    private LicenseService licenseService;
    @Autowired
    private MockMvc mockMvc;
    private static String BASEURL;

    @BeforeAll
    static void setBaseUrl() {
        BASEURL = "/v1/organization/{organizationId}/license";
    }

    @BeforeEach
    void configureSystemUnderTest() {
        requestBuilder = new LicenseRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("find licence by license id and organization id")
    class GetLicenceByLicenseIdAndOrganizationId{
        private static final String LICENSE_ID = "12345678";
        private static final String ORGANIZATION_ID = "OptimaGrowth";
        @Nested
        @DisplayName("When the requested license is not found")
        class WhenRequestedLicenseIsNotFound{
            @BeforeEach
            void throwException() {
                when(licenseService.getLicense(LICENSE_ID, ORGANIZATION_ID))
                        .thenThrow(new ResourceNotFoundException(
                                "Unable to find license with License id"));
            }

            @Test
            @DisplayName("Should return the HTTP status code not found (404)")
            void shouldReturnHttpStatusCodeNotFound() throws Exception {
                requestBuilder.getLicenseByIdAndOrganizationId(BASEURL, LICENSE_ID, ORGANIZATION_ID)
                        .andExpect(status().isNotFound());
            }

            @Test
            @DisplayName("Should throws IllegalArgumentException which has a message error")
            void shouldThrowsIllegalArgumentException() throws Exception {
                requestBuilder.getLicenseByIdAndOrganizationId(BASEURL, LICENSE_ID, ORGANIZATION_ID)
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                        .andExpect(result -> assertEquals("Unable to find license with License id",
                                result.getResolvedException().getMessage()));
            }
        }
        @Nested
        @DisplayName("When the requested license is found")
        class WhenRequestedLicenseIsFound{
            private static final String DESCRIPTION = "description";
            private static final String PRODUCT_NAME = "productName";
            private static final String LICENSE_TYPE  = "licenseType";
            private static final String COMMENT = "I AM DEFAULT";

            @BeforeEach
            void returnFoundLicense() {
                License license = new License(
                        LICENSE_ID, DESCRIPTION, ORGANIZATION_ID,
                        PRODUCT_NAME, LICENSE_TYPE, COMMENT);
                when(licenseService.getLicense(LICENSE_ID, ORGANIZATION_ID)).thenReturn(license);
            }

            @Test
            @DisplayName("Should return the HTTP status code ok (200)")
            void shouldReturnHttpStatusCodeOk() throws Exception {
                requestBuilder.getLicenseByIdAndOrganizationId(BASEURL, LICENSE_ID, ORGANIZATION_ID)
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("Should return the information of the found license as HAL+JSON")
            void shouldReturnInformationOfFoundTodoItemAsJSON() throws Exception {
                requestBuilder.getLicenseByIdAndOrganizationId(BASEURL, LICENSE_ID, ORGANIZATION_ID)
                        .andExpect(content().contentType("application/hal+json"));
            }

            @Test
            @DisplayName("Should return the correct information of the found license")
            void getLicense() throws Exception {
                requestBuilder.getLicenseByIdAndOrganizationId(BASEURL, LICENSE_ID, ORGANIZATION_ID)
                        .andExpect(jsonPath("$.licenseId", is(LICENSE_ID)))
                        .andExpect(jsonPath("$.organizationId", is(ORGANIZATION_ID)))
                        .andExpect(jsonPath("$.description", equalTo(DESCRIPTION)))
                        .andExpect(jsonPath("$.productName", equalTo(PRODUCT_NAME)))
                        .andExpect(jsonPath("$.licenseType", equalTo(LICENSE_TYPE)))
                        .andExpect(jsonPath("$.comment", equalTo(COMMENT)))
                        .andExpect(jsonPath("$", hasKey("_links")))
                        .andExpect(jsonPath("$._links.length()", is(4)));
            }
        }
    }

    @Test
    void updateLicense() {
    }

    @Test
    void createLicense() {
    }

    @Test
    void deleteLicense() {
    }
}