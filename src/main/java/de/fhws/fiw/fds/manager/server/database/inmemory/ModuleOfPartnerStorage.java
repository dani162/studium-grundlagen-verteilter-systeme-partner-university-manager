package de.fhws.fiw.fds.manager.server.database.inmemory;

import de.fhws.fiw.fds.manager.server.api.models.Module;
import de.fhws.fiw.fds.manager.server.database.ModuleOfPartnerDao;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;

public class ModuleOfPartnerStorage extends AbstractInMemoryStorage<Module> implements ModuleOfPartnerDao {
    public ModuleOfPartnerStorage() {
        super();
        this.populateData();
    }

    private void populateData() {
    }
}
