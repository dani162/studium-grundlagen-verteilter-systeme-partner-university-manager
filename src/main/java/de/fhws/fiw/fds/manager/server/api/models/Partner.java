package de.fhws.fiw.fds.manager.server.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.fhws.fiw.fds.manager.server.api.states.partner.PartnerUri;
import de.fhws.fiw.fds.manager.server.api.states.partner_modules.ModuleOfPartnerRelTypes;
import de.fhws.fiw.fds.manager.server.api.states.partner_modules.ModuleOfPartnerUri;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.time.LocalDate;

@JsonRootName("partner")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Partner extends AbstractModel {
    private String name;
    private String country;
    private String department;
    private String website;
    private String contactPerson;
    private int numberOfSendableStudents;
    private int numberOfAcceptableStudents;
    private LocalDate startOfNextSpringSemester;
    private LocalDate startOfNextAutumnSemester;

    @SuttonLink(value = PartnerUri.PATH_ELEMENT + "/${id}", rel = "self")
    private transient Link selfLink;
    @SuttonLink(value = PartnerUri.PATH_ELEMENT + "/${id}/" + ModuleOfPartnerUri.PATH_ELEMENT, rel = ModuleOfPartnerRelTypes.GET_ALL_MODULES_OF_PARTNER)
    private transient Link modules;

    public Partner() {}

    public Partner(String name, String country, String department, String website, String contactPerson, int numberOfSendableStudents, int numberOfAcceptableStudents, LocalDate startOfNextSpringSemester, LocalDate startOfNextAutumnSemester) {
        this.name = name;
        this.country = country;
        this.department = department;
        this.website = website;
        this.contactPerson = contactPerson;
        this.numberOfSendableStudents = numberOfSendableStudents;
        this.numberOfAcceptableStudents = numberOfAcceptableStudents;
        this.startOfNextSpringSemester = startOfNextSpringSemester;
        this.startOfNextAutumnSemester = startOfNextAutumnSemester;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", department='" + department + '\'' +
                ", website='" + website + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", numberOfSendableStudents=" + numberOfSendableStudents +
                ", numberOfAcceptableStudents=" + numberOfAcceptableStudents +
                ", startOfNextSpringSemester=" + startOfNextSpringSemester +
                ", startOfNextAutumnSemester=" + startOfNextAutumnSemester +
                '}';
    }

    //<editor-fold desc="getter & setter">
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    public Link getModules() {
        return modules;
    }

    public void setModules(Link modules) {
        this.modules = modules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getNumberOfSendableStudents() {
        return numberOfSendableStudents;
    }

    public void setNumberOfSendableStudents(int numberOfSendableStudents) {
        this.numberOfSendableStudents = numberOfSendableStudents;
    }

    public int getNumberOfAcceptableStudents() {
        return numberOfAcceptableStudents;
    }

    public void setNumberOfAcceptableStudents(int numberOfAcceptableStudents) {
        this.numberOfAcceptableStudents = numberOfAcceptableStudents;
    }

    public LocalDate getStartOfNextSpringSemester() {
        return startOfNextSpringSemester;
    }

    public void setStartOfNextSpringSemester(LocalDate startOfNextSpringSemester) {
        this.startOfNextSpringSemester = startOfNextSpringSemester;
    }

    public LocalDate getStartOfNextAutumnSemester() {
        return startOfNextAutumnSemester;
    }

    public void setStartOfNextAutumnSemester(LocalDate startOfNextAutumnSemester) {
        this.startOfNextAutumnSemester = startOfNextAutumnSemester;
    }
    //</editor-fold>
}
