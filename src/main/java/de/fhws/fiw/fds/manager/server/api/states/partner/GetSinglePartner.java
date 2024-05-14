package de.fhws.fiw.fds.manager.server.api.states.partner;

import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.manager.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.Response;

public class GetSinglePartner extends AbstractGetState<Response, Partner> {
    public GetSinglePartner(ServiceContext serviceContext, long requestedId) {
        super(serviceContext, requestedId);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Partner> loadModel() {
        return DaoFactory.getInstance().getPartnerDao().readById(this.requestedId);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PartnerUri.REL_PATH, PartnerRelTypes.GET_ALL_PARTNERS, getAcceptRequestHeader());
        addLink(PartnerUri.REL_PATH_ID, PartnerRelTypes.DELETE_SINGLE_PARTNER, getAcceptRequestHeader(),
                this.requestedId);
        addLink(PartnerUri.REL_PATH_ID, PartnerRelTypes.UPDATE_SINGLE_PARTNER, getAcceptRequestHeader(),
                this.requestedId);
    }
}
