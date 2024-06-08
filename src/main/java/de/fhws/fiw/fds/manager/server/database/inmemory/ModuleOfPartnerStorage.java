package de.fhws.fiw.fds.manager.server.database.inmemory;

import de.fhws.fiw.fds.manager.server.api.models.Module;
import de.fhws.fiw.fds.manager.server.database.ModuleDao;
import de.fhws.fiw.fds.manager.server.database.ModuleOfPartnerDao;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryRelationStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class ModuleOfPartnerStorage extends AbstractInMemoryRelationStorage<Module> implements ModuleOfPartnerDao {
    final private ModuleDao moduleStorage;

    public ModuleOfPartnerStorage(ModuleDao moduleStorage) {
        super();
        this.moduleStorage = moduleStorage;
    }

    @Override
    protected IDatabaseAccessObject<Module> getSecondaryStorage() {
        return this.moduleStorage;
    }

    @Override
    public CollectionModelResult<Module> readAllLinked(long primaryId, SearchParameter searchParameter) {
        return InMemoryPaging.page(
                this.readAllLinkedByPredicate(primaryId, (p) -> true),
                searchParameter.getOffset(), searchParameter.getSize()
        );
    }

    @Override
    public void resetDatabase() {
        this.storage.clear();
    }
}
