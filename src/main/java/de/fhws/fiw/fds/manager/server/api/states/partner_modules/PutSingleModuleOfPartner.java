package de.fhws.fiw.fds.manager.server.api.states.partner_modules;

import de.fhws.fiw.fds.manager.server.api.models.Module;
import de.fhws.fiw.fds.manager.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.Response;

public class PutSingleModuleOfPartner extends AbstractPutRelationState<Response, Module> {

    public PutSingleModuleOfPartner(ServiceContext serviceContext, long primaryId, long requestedId, Module modelToUpdate) {
        super(serviceContext, primaryId, requestedId, modelToUpdate);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Module> loadModel() {
        return DaoFactory.getInstance().getModuleDao().readById(this.requestedId);
    }

    @Override
    protected NoContentResult updateModel() {
        return DaoFactory.getInstance().getModuleOfPartnerDao().update(this.primaryId, this.modelToUpdate);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(ModuleOfPartnerUri.REL_PATH_ID,
                ModuleOfPartnerRelTypes.GET_SINGLE_MODULE_OF_PARTNER,
                getAcceptRequestHeader(),
                this.primaryId, this.requestedId);
    }
}
