package de.fhws.fiw.fds.manager.server.api.states.partner;

import de.fhws.fiw.fds.manager.Start;

import static de.fhws.fiw.fds.manager.Constants.APPLICATION_PATH;

public interface PartnerUri {
    String PATH_ELEMENT = "partners";
    String REL_PATH = Start.CONTEXT_PATH + "/" + APPLICATION_PATH + "/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";
}
