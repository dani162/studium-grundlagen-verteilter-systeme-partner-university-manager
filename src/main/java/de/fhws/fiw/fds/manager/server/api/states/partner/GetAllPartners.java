package de.fhws.fiw.fds.manager.server.api.states.partner;

import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import jakarta.ws.rs.core.Response;

public class GetAllPartners extends AbstractGetCollectionState<Response, Partner> {

    public GetAllPartners(ServiceContext serviceContext, AbstractQuery<Response, Partner> query) {
        super(serviceContext, query);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PartnerUri.REL_PATH, PartnerRelTypes.CREATE_PARTNER, getAcceptRequestHeader());
        addLink(PartnerUri.REL_PATH + "?name={NAME}&country={COUNTRY}", getAcceptRequestHeader());
    }
}
