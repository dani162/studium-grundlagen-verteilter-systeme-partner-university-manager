package de.fhws.fiw.fds.manager.server.api.states.dispatcher;

import de.fhws.fiw.fds.manager.server.api.states.partner.PartnerRelTypes;
import de.fhws.fiw.fds.manager.server.api.states.partner.PartnerUri;
import de.fhws.fiw.fds.manager.server.api.states.partner_modules.ModuleOfPartnerRelTypes;
import de.fhws.fiw.fds.manager.server.api.states.partner_modules.ModuleOfPartnerUri;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetDispatcherState;
import jakarta.ws.rs.core.Response;

public class GetDispatcher extends AbstractGetDispatcherState<Response> {

    public GetDispatcher(ServiceContext serviceContext) {
        super(serviceContext);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PartnerUri.REL_PATH, PartnerRelTypes.GET_ALL_PARTNERS, getAcceptRequestHeader());
        addLink(ModuleOfPartnerUri.REL_PATH, ModuleOfPartnerRelTypes.GET_ALL_MODULES_OF_PARTNER, getAcceptRequestHeader());
    }
}
