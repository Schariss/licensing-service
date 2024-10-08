package com.optimagrowth.license.service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.exception.ResourceNotFoundException;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LicenseService {

    private final MessageSource  messageSource;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;


    public License getLicense(String licenseId, String organizationId) throws ResourceNotFoundException{
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
            throw new ResourceNotFoundException(
                    String.format(
                            messageSource.getMessage("license.search.error.message", null, null),
                            licenseId,
                            organizationId));
        }

        return license.withComment(config.getProperty());
    }

    public List<License> getLicensesByOrganizationId(String organizationId) {
        List<License> licenses = licenseRepository.findByOrganizationId(organizationId)
                .stream().map(l -> l.withComment(config.getProperty())).collect(Collectors.toList());
        return licenses;
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
                messageSource.getMessage("license.delete.message", null, null), licenseId);
    }
}
