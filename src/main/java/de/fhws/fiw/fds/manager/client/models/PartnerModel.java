package de.fhws.fiw.fds.manager.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;

import java.time.LocalDate;
import java.util.Objects;

public class PartnerModel extends AbstractClientModel {
    private String name;
    private String country;
    private String department;
    private String website;
    private String contactPerson;
    private int numberOfSendableStudents;
    private int numberOfAcceptableStudents;
    private LocalDate startOfNextSpringSemester;
    private LocalDate startOfNextAutumnSemester;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link selfLink;
    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link modules;

    public PartnerModel() {
    }

    public PartnerModel(String name, String country, String department, String website, String contactPerson, int numberOfSendableStudents, int numberOfAcceptableStudents, LocalDate startOfNextSpringSemester, LocalDate startOfNextAutumnSemester, Link selfLink, Link modules) {
        this.name = name;
        this.country = country;
        this.department = department;
        this.website = website;
        this.contactPerson = contactPerson;
        this.numberOfSendableStudents = numberOfSendableStudents;
        this.numberOfAcceptableStudents = numberOfAcceptableStudents;
        this.startOfNextSpringSemester = startOfNextSpringSemester;
        this.startOfNextAutumnSemester = startOfNextAutumnSemester;
        this.selfLink = selfLink;
        this.modules = modules;
    }

    //<editor-fold desc="getter & setter">
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

    @JsonIgnore
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    @JsonIgnore
    public Link getModules() {
        return modules;
    }

    public void setModules(Link modules) {
        this.modules = modules;
    }
    //</editor-fold>


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartnerModel that = (PartnerModel) o;
        return numberOfSendableStudents == that.numberOfSendableStudents && numberOfAcceptableStudents == that.numberOfAcceptableStudents && Objects.equals(name, that.name) && Objects.equals(country, that.country) && Objects.equals(department, that.department) && Objects.equals(website, that.website) && Objects.equals(contactPerson, that.contactPerson) && Objects.equals(startOfNextSpringSemester, that.startOfNextSpringSemester) && Objects.equals(startOfNextAutumnSemester, that.startOfNextAutumnSemester);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(country);
        result = 31 * result + Objects.hashCode(department);
        result = 31 * result + Objects.hashCode(website);
        result = 31 * result + Objects.hashCode(contactPerson);
        result = 31 * result + numberOfSendableStudents;
        result = 31 * result + numberOfAcceptableStudents;
        result = 31 * result + Objects.hashCode(startOfNextSpringSemester);
        result = 31 * result + Objects.hashCode(startOfNextAutumnSemester);
        return result;
    }
}
