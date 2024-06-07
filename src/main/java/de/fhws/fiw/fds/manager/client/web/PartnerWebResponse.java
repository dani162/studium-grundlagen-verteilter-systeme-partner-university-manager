package de.fhws.fiw.fds.manager.client.web;

import de.fhws.fiw.fds.manager.client.models.PartnerModel;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;
import okhttp3.Headers;

import java.util.Collection;

public class PartnerWebResponse extends WebApiResponse<PartnerModel> {
    public PartnerWebResponse(
            Collection<PartnerModel> responseData,
            Headers responseHeaders,
            int lastStatusCode
    ) {
        super(responseData, responseHeaders, lastStatusCode);
    }
}
