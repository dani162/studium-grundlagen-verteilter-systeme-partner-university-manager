package de.fhws.fiw.fds.manager.client.web;

import de.fhws.fiw.fds.manager.client.models.DispatcherModel;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;
import okhttp3.Headers;

import java.util.Collection;

public class DispatcherWebResponse extends WebApiResponse<DispatcherModel> {
    public DispatcherWebResponse(
            final Collection<DispatcherModel> responseData,
            final Headers headers,
            final int lastStatusCode) {
        super(responseData, headers, lastStatusCode);
    }
}
