package de.fhws.fiw.fds.manager.client.web;

import de.fhws.fiw.fds.manager.client.models.ModuleModel;
import de.fhws.fiw.fds.sutton.client.web.GenericWebClient;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;

import java.io.IOException;

public class ModuleWebClient {
    private final GenericWebClient<ModuleModel> client;

    public ModuleWebClient() {
        this.client = new GenericWebClient<>();
    }

    public ModuleWebResponse getCollectionOfModule(String url) throws IOException {
        return createResponse(this.client.sendGetCollectionRequest(url, ModuleModel.class));
    }

    public ModuleWebResponse getSingleModule(String url) throws IOException {
        return createResponse(this.client.sendGetSingleRequest(url, ModuleModel.class));
    }

    public ModuleWebResponse postNewModule(String url, ModuleModel partner) throws IOException {
        return createResponse(this.client.sendPostRequest(url, partner));
    }

    public ModuleWebResponse putNewModule(String url, ModuleModel partner) throws IOException {
        return createResponse(this.client.sendPutRequest(url, partner));
    }

    public ModuleWebResponse deleteModule(String url) throws IOException {
        return createResponse(this.client.sendDeleteRequest(url));
    }

    private ModuleWebResponse createResponse(WebApiResponse<ModuleModel> response) {
        return new ModuleWebResponse(
                response.getResponseData(),
                response.getResponseHeaders(),
                response.getLastStatusCode()
        );
    }
}
