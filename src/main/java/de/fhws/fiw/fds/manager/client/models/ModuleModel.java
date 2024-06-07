package de.fhws.fiw.fds.manager.client.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;

public class ModuleModel extends AbstractClientModel {
    private String name;
    private int SemesterType;
    private float numberOfCreditPoints;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link selfLink;

    public ModuleModel() {}

    public ModuleModel(String name, int semesterType, float numberOfCreditPoints, Link selfLink) {
        this.name = name;
        SemesterType = semesterType;
        this.numberOfCreditPoints = numberOfCreditPoints;
        this.selfLink = selfLink;
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

    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }
    //</editor-fold>
}
