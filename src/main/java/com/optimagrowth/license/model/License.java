package com.optimagrowth.license.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter @ToString
/***
 * @Entity annotation
 * lets Spring know that this Java POJO is going to be mapping objects that will hold data
 */
@Entity
@Table(name = "licenses")
/***
 * RepresentationModel<License> gives us the ability to add links to the License model class
*/
public class License extends RepresentationModel<License> {
    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;
    private String description;
    @Column(name = "organization_id", nullable = false)
    private String organizationId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "license_type", nullable = false)
    private String licenseType;
    private String comment;

    public License withComment(String comment){
        this.setComment(comment);
        return this;
    }

}
