package com.optimagrowth.license.repository;

import com.optimagrowth.license.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/***
 * @author Adnane Chahid
 * tells Spring that it should treat this interface as a repository and generate a dynamic proxy for it
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

    /***
     * find list of licenses related to an organization
     * @author Adnane Chahid
     * @param organizationId organization id corresponding to the license
     * @return List<License> list of related licenses
     * At startup, Spring will parse the name of the methods from the Repository interface,
     * convert them to an SQL statement based on the names, and then generate a dynamic proxy class to do the work
     */
    List<License> findByOrganizationId(String organizationId);

    /***
     * find single license by its id and organization id
     * @author Adnane Chahid
     * @param organizationId organization id corresponding to the license
     * @param licenseId license id
     * @return License license object
     */
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
