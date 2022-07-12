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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest does not scan beans for our services, so we have to provide a bean
// for anything that the controller depends on
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
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.licenseId", is(licenseId)))
                .andExpect(jsonPath("$.organizationId", is(organizationId)))
                .andDo(print());
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