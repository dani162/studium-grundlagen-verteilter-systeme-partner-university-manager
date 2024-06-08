package de.fhws.fiw.fds.manager.server.api.states.partner_modules;

import de.fhws.fiw.fds.manager.server.api.models.Module;
import de.fhws.fiw.fds.manager.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.Response;

public class GetSingleModuleOfPartner extends AbstractGetRelationState<Response, Module> {

    public GetSingleModuleOfPartner(ServiceContext serviceContext, long primaryId, long requestedId) {
        super(serviceContext, primaryId, requestedId);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Module> loadModel() {
        SingleModelResult<Module> module = DaoFactory.getInstance().getModuleDao().readById(this.requestedId);
        if (isModuleOfPartner()) {
            module.getResult().setPrimaryId(this.primaryId);
        }
        // TODO: Check if module is only returned if its exits for the selected specific university
        // TODO: how to return not found here? null?
        return module;
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(ModuleOfPartnerUri.REL_PATH, ModuleOfPartnerRelTypes.GET_ALL_MODULES_OF_PARTNER, getAcceptRequestHeader(),
                this.primaryId);
        addLink(ModuleOfPartnerUri.REL_PATH_ID, ModuleOfPartnerRelTypes.DELETE_SINGLE_MODULE_OF_PARTNER, getAcceptRequestHeader(),
                this.primaryId, this.requestedId);
        addLink(ModuleOfPartnerUri.REL_PATH_ID, ModuleOfPartnerRelTypes.UPDATE_SINGLE_MODULE_OF_PARTNER, getAcceptRequestHeader(),
                this.primaryId, this.requestedId);
    }

    private boolean isModuleOfPartner() {
        return !DaoFactory.getInstance().getModuleOfPartnerDao()
                .readById(this.primaryId, this.requestedId).isEmpty();
    }
}
