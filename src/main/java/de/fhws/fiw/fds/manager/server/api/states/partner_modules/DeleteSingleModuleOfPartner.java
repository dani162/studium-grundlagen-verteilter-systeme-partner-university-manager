package de.fhws.fiw.fds.manager.server.api.states.partner_modules;

import de.fhws.fiw.fds.manager.server.api.models.Module;
import de.fhws.fiw.fds.manager.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.Response;

public class DeleteSingleModuleOfPartner extends AbstractDeleteRelationState<Response, Module> {

    public DeleteSingleModuleOfPartner(ServiceContext serviceContext, long modelIdToDelete, long primaryId) {
        super(serviceContext, modelIdToDelete, primaryId);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Module> loadModel() {
        return DaoFactory.getInstance().getModuleOfPartnerDao().readById(this.primaryId, this.modelIdToDelete);
    }

    @Override
    protected NoContentResult deleteModel() {
        return DaoFactory.getInstance().getModuleOfPartnerDao().deleteRelation(this.primaryId, this.modelIdToDelete);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(ModuleOfPartnerUri.REL_PATH,
                ModuleOfPartnerRelTypes.GET_ALL_MODULES_OF_PARTNER,
                getAcceptRequestHeader(),
                this.primaryId);
    }
}
