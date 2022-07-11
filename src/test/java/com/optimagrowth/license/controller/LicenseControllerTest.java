package com.optimagrowth.license.controller;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LicenseController.class)
class LicenseControllerTest {

    @MockBean
    private LicenseService licenseService;
    @Autowired
    private MockMvc mockMvc;
    private static String baseUrl;

    @BeforeAll
    static void beforeAll() {
        baseUrl = "http://localhost:8080/v1/organization/{organizationId}/license";
    }

    @Test
    void getLicense() throws Exception {
        String organizationId = "OptimaGrowth";
        String licenseId = "1234";
        License license = new License(licenseId, "description", organizationId, "productName",
                "licenseType", "I AM DEFAULT");
        when(licenseService.getLicense(licenseId, organizationId)).thenReturn(license);
        mockMvc.perform(get(baseUrl + "/{licenseId}", organizationId, licenseId))
                .andExpect(status().isOk());
                //.andExpect();
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