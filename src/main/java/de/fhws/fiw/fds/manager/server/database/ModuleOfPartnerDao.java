package de.fhws.fiw.fds.manager.server.database;

import de.fhws.fiw.fds.manager.server.api.models.Module;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseRelationAccessObject;


public interface ModuleOfPartnerDao extends IDatabaseRelationAccessObject<Module> {

    void resetDatabase();
}
