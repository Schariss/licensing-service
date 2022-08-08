package com.optimagrowth.license.controller;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class LicenseRequestBuilder {
    private final MockMvc mockMvc;

    public LicenseRequestBuilder(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }

    /***
     * Creates and sends the HTTP request which gets the corresponding license
     * @param baseUrl The base url of the LicenseController
     * @param licenseId license id
     * @param organizationId organization id corresponding to the license
     * @return ResultActions on which we are going to write assertions for the HTTP response returned
     * @throws Exception thrown by the perform method
     */
    public ResultActions getLicenseByIdAndOrganizationId(String baseUrl, String licenseId, String organizationId) throws Exception {
        return mockMvc.perform(get(baseUrl + "/{licenseId}", organizationId, licenseId));
    }
}
