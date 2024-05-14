package de.fhws.fiw.fds.manager.server.api.states.partner;

import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.manager.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.Response;

public class PutSinglePartner extends AbstractPutState<Response, Partner> {
    public PutSinglePartner(ServiceContext serviceContext, long requestedId, Partner modelToUpdate) {
        super(serviceContext, requestedId, modelToUpdate);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Partner> loadModel() {
        return DaoFactory.getInstance().getPartnerDao().readById(this.modelToUpdate.getId());
    }

    @Override
    protected NoContentResult updateModel() {
        return DaoFactory.getInstance().getPartnerDao().update(this.modelToUpdate);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PartnerUri.REL_PATH_ID, PartnerRelTypes.GET_SINGLE_PARTNER, getAcceptRequestHeader(),
                this.modelToUpdate.getId());
    }
}
