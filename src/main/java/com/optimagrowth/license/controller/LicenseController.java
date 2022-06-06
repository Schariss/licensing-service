package com.optimagrowth.license.controller;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/*
    Tells Spring Boot that this is a REST-based service and it will automatically
    serialize/deserialize service requests/responses via JSON

    Unlike the traditional Spring @Controller annotation, @RestController doesn’t require you
    to return a ResponseBody class from your method in the controller class. This is all handled
    by the presence of the @RestController annotation, which includes the @ResponseBody annotation.
 */
@RestController
/*
    The {organizationId} is a placeholder that indicates how you expect the URL to be parameterized
    with an organizationId passed in every call. The use of organizationId in the URL
    allows you to differentiate between the different customers who might use your service
*/
@RequestMapping(value = "v1/organization/{organizationId}/license")
@AllArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @GetMapping(value="/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        License license = licenseService.getLicense(licenseId,organizationId);
        license.add(linkTo(methodOn(LicenseController.class)
                .getLicense(licenseId, organizationId)).withSelfRel());
        license.add(linkTo(methodOn(LicenseController.class)
                .updateLicense(organizationId, license)).withRel("updateLicense"));
        license.add(linkTo(methodOn(LicenseController.class)
                .createLicense(organizationId, license, null)).withRel("createLicense"));
        license.add(linkTo(methodOn(LicenseController.class)
                .deleteLicense(organizationId, licenseId)).withRel("deleteLicense"));
        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request, organizationId));
    }

    @PostMapping
    public ResponseEntity<String> createLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.createLicense(request, organizationId, locale));
    }

    @DeleteMapping(value="/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId));
    }

}
