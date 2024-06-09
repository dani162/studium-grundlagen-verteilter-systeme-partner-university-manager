package de.fhws.fiw.fds.manager.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;

import java.util.Objects;

public class ModuleModel extends AbstractClientModel {
    private String name;
    private int SemesterType;
    private float numberOfCreditPoints;

//    @JsonDeserialize(using = ClientLinkJsonConverter.class)
//    private Link selfLink;
    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link  selfLinkOnSecond;

    public ModuleModel() {}

    public ModuleModel(String name, int semesterType, float numberOfCreditPoints, /*Link selfLink,*/ Link selfLinkOnSecond) {
        this.name = name;
        SemesterType = semesterType;
        this.numberOfCreditPoints = numberOfCreditPoints;
//        this.selfLink = selfLink;
        this.selfLinkOnSecond = selfLinkOnSecond;
    }

    //<editor-fold desc="getter & setter">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemesterType() {
        return SemesterType;
    }

    public void setSemesterType(int semesterType) {
        SemesterType = semesterType;
    }

    public float getNumberOfCreditPoints() {
        return numberOfCreditPoints;
    }

    public void setNumberOfCreditPoints(float numberOfCreditPoints) {
        this.numberOfCreditPoints = numberOfCreditPoints;
    }

//    @JsonIgnore
//    public Link getSelfLink() {
//        return selfLink;
//    }
//
//    public void setSelfLink(Link selfLink) {
//        this.selfLink = selfLink;
//    }

    @JsonIgnore
    public Link getSelfLinkOnSecond() {
        return selfLinkOnSecond;
    }

    public void setSelfLinkOnSecond(Link selfLinkOnSecond) {
        this.selfLinkOnSecond = selfLinkOnSecond;
    }
    //</editor-fold>


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModuleModel that = (ModuleModel) o;
        return SemesterType == that.SemesterType && Float.compare(numberOfCreditPoints, that.numberOfCreditPoints) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + SemesterType;
        result = 31 * result + Float.hashCode(numberOfCreditPoints);
        return result;
    }
}
