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
     * @author Adnane Chahid
     * @param organizationId
     * @return List<License>
     * At startup, Spring will parse the name of the methods from the Repository interface,
     * convert them to an SQL statement based on the names, and then generate a dynamic proxy class to do the work
     */
    public List<License> findByOrganizationId(String organizationId);

    /***
     * @author Adnane Chahid
     * @param organizationId
     * @param licenseId
     * @return License
     */
    public License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
