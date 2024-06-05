package de.fhws.fiw.fds.manager.server.api.states.partner_modules;

import de.fhws.fiw.fds.manager.server.api.models.Module;
import de.fhws.fiw.fds.manager.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.ws.rs.core.Response;

public class PostNewModuleOfPartner extends AbstractPostRelationState<Response, Module> {

    public PostNewModuleOfPartner(ServiceContext serviceContext, long primaryId, Module modelToStore) {
        super(serviceContext, primaryId, modelToStore);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected NoContentResult saveModel() {
        return DaoFactory.getInstance().getModuleOfPartnerDao().create(this.primaryId, this.modelToStore);
    }

    @Override
    protected void defineTransitionLinks() {

    }
}
