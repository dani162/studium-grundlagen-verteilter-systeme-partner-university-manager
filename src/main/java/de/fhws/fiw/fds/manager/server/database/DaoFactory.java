package de.fhws.fiw.fds.manager.server.database;

import de.fhws.fiw.fds.manager.server.database.inmemory.ModuleOfPartnerStorage;
import de.fhws.fiw.fds.manager.server.database.inmemory.ModuleStorage;
import de.fhws.fiw.fds.manager.server.database.inmemory.PartnerStorage;

public class DaoFactory {

    private static DaoFactory INSTANCE;

    public static DaoFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DaoFactory();
        }
        return INSTANCE;
    }

    private final PartnerDao partnerDao;
    private final ModuleDao moduleDao;
    private final ModuleOfPartnerDao moduleOfPartnerDao;

    private DaoFactory() {
        this.partnerDao = new PartnerStorage();
        this.moduleDao = new ModuleStorage();
        this.moduleOfPartnerDao = new ModuleOfPartnerStorage(this.moduleDao);
    }

    public PartnerDao getPartnerDao() {
        return partnerDao;
    }

    public ModuleDao getModuleDao() {
        return moduleDao;
    }

    public ModuleOfPartnerDao getModuleOfPartnerDao() {
        return moduleOfPartnerDao;
    }
}
