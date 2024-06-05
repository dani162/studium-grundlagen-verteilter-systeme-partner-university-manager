package de.fhws.fiw.fds.manager.server.api.states.partner_modules;

import de.fhws.fiw.fds.manager.server.api.models.Module;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionRelationState;
import jakarta.ws.rs.core.Response;

public class GetAllModulesOfPartner extends AbstractGetCollectionRelationState<Response, Module> {

    public GetAllModulesOfPartner(ServiceContext serviceContext, long primaryId, AbstractRelationQuery<Response, Module> query) {
        super(serviceContext, primaryId, query);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(ModuleOfPartnerUri.REL_PATH,
                ModuleOfPartnerRelTypes.CREATE_MODULE,
                getAcceptRequestHeader(),
                this.primaryId);
    }
}
