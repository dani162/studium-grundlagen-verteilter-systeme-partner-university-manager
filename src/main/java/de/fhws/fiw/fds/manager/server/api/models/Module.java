package de.fhws.fiw.fds.manager.server.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.fhws.fiw.fds.manager.server.api.states.partner_modules.ModuleOfPartnerUri;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SelfLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

@JsonRootName("module")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Module extends AbstractModel {
    private String name;
    private int SemesterType;
    private float numberOfCreditPoints;

    @SelfLink(pathElement = ModuleOfPartnerUri.PATH_ELEMENT)
    private transient Link selfLink;

    public Module() {}

    public Module(String name, int semesterType, float numberOfCreditPoints) {
        this.name = name;
        SemesterType = semesterType;
        this.numberOfCreditPoints = numberOfCreditPoints;
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
