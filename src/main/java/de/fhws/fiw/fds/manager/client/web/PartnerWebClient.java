package de.fhws.fiw.fds.manager.client.web;

import de.fhws.fiw.fds.manager.client.models.PartnerModel;
import de.fhws.fiw.fds.sutton.client.web.GenericWebClient;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;

import java.io.IOException;

public class PartnerWebClient {
    private GenericWebClient<PartnerModel> client;

    public PartnerWebClient() {
        this.client = new GenericWebClient<>();
    }

    public PartnerWebResponse getCollectionOfPartner(String url) throws IOException {
        return createResponse(this.client.sendGetCollectionRequest(url, PartnerModel.class));
    }

    public PartnerWebResponse getSinglePartner(String url) throws IOException {
        return createResponse(this.client.sendGetSingleRequest(url, PartnerModel.class));
    }

    public PartnerWebResponse postNewPartner(String url, PartnerModel partner) throws IOException {
        return createResponse(this.client.sendPostRequest(url, partner));
    }

    public PartnerWebResponse putNewPartner(String url, PartnerModel partner) throws IOException {
        return createResponse(this.client.sendPutRequest(url, partner));
    }

    public PartnerWebResponse deletePartner(String url) throws IOException {
        return createResponse(this.client.sendDeleteRequest(url));
    }

    private PartnerWebResponse createResponse(WebApiResponse<PartnerModel> response) {
        return new PartnerWebResponse(
                response.getResponseData(),
                response.getResponseHeaders(),
                response.getLastStatusCode()
        );
    }
}
