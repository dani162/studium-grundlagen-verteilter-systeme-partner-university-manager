package de.fhws.fiw.fds.manager.client.web;
import de.fhws.fiw.fds.manager.client.models.ModuleModel;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;
import okhttp3.Headers;

import java.util.Collection;

public class ModuleWebResponse extends WebApiResponse<ModuleModel> {
    public ModuleWebResponse(
            final Collection<ModuleModel> responseData,
            final Headers headers,
            final int lastStatusCode) {
        super(responseData, headers, lastStatusCode);
    }
}
