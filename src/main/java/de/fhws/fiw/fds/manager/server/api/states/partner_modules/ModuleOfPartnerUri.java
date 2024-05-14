package de.fhws.fiw.fds.manager.server.api.states.partner_modules;

import de.fhws.fiw.fds.manager.Start;
import de.fhws.fiw.fds.manager.server.api.states.partner.PartnerUri;

import static de.fhws.fiw.fds.manager.Constants.APPLICATION_PATH;

public interface ModuleOfPartnerUri {
    String PATH_ELEMENT = "modules";

    String REL_PATH = PartnerUri.REL_PATH_ID + "/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";
}
