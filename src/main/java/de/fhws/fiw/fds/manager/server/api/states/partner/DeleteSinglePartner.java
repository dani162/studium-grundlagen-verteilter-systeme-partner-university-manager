package de.fhws.fiw.fds.manager.server.api.states.partner;

import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.manager.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.Response;

public class DeleteSinglePartner extends AbstractDeleteState<Response, Partner> {
    public DeleteSinglePartner(ServiceContext serviceContext, long modelIdToDelete) {
        super(serviceContext, modelIdToDelete);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Partner> loadModel() {
        return DaoFactory.getInstance().getPartnerDao().readById(this.modelIdToDelete);
    }

    @Override
    protected NoContentResult deleteModel() {
        return DaoFactory.getInstance().getPartnerDao().delete(this.modelIdToDelete);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PartnerUri.REL_PATH, PartnerRelTypes.GET_ALL_PARTNERS, getAcceptRequestHeader());
    }
}
