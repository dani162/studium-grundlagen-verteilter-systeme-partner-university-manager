package de.fhws.fiw.fds.manager.server.database.utils;

import de.fhws.fiw.fds.manager.server.database.DaoFactory;

public class ResetDatabase {

    public static void resetAll() {
        DaoFactory.getInstance().getModuleDao().resetDatabase();
        DaoFactory.getInstance().getPartnerDao().resetDatabase();
    }

}
