package com.optimagrowth.license.service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.exception.ResourceNotFoundException;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {

    @Qualifier("messageSource")
    @Autowired
    MessageSource messages;
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    private ServiceConfig config;

    public License getLicense(String licenseId, String organizationId) throws ResourceNotFoundException{
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
            throw new ResourceNotFoundException(
                    String.format(
                            messages.getMessage("license.search.error.message", null, null),
                            licenseId,
                            organizationId));
        }

        return license.withComment(config.getProperty());
    }

    public License createLicense(License license, String organizationId){
        license.setLicenseId(UUID.randomUUID().toString());
        license.setOrganizationId(organizationId);
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license){
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId){
        licenseRepository.deleteById(licenseId);
        return String.format(
                messages.getMessage("license.delete.message", null, null), licenseId);
    }
}
