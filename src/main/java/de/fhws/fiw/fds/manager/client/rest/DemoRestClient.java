package de.fhws.fiw.fds.manager.client.rest;

import de.fhws.fiw.fds.manager.Constants;
import de.fhws.fiw.fds.manager.client.models.ModuleModel;
import de.fhws.fiw.fds.manager.client.models.PartnerModel;
import de.fhws.fiw.fds.manager.client.web.DispatcherWebClient;
import de.fhws.fiw.fds.manager.client.web.ModuleWebClient;
import de.fhws.fiw.fds.manager.client.web.PartnerWebClient;
import de.fhws.fiw.fds.manager.server.api.states.partner.PartnerRelTypes;
import de.fhws.fiw.fds.manager.server.api.states.partner_modules.ModuleOfPartnerRelTypes;
import de.fhws.fiw.fds.sutton.client.rest2.AbstractRestClient;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DemoRestClient extends AbstractRestClient {
    private static final String BASE_URL = Constants.BASE_URL;

    final private DispatcherWebClient dispatcherWebClient;
    final private PartnerWebClient partnerWebClient;
    final private ModuleWebClient moduleWebClient;

    private List<PartnerModel> currentPartnerData;
    private int cursorPartnerData = 0;

    private List<ModuleModel> currentModuleData;
    private int cursorModuleData = 0;

    public DemoRestClient() {
        super();
        this.dispatcherWebClient = new DispatcherWebClient();
        this.partnerWebClient = new PartnerWebClient();
        this.moduleWebClient = new ModuleWebClient();
    }

    public void start() throws IOException {
        processResponse(this.dispatcherWebClient.getDispatcher(BASE_URL), (response -> {
        }));
    }

    //<editor-fold desc="Partner">
    public boolean isGetAllPartnersAllowed() {
        return isLinkAvailable(PartnerRelTypes.GET_ALL_PARTNERS);
    }
    public void getAllPartners() throws IOException {
        if (isGetAllPartnersAllowed()) {
            processResponse(
                    this.partnerWebClient.getCollectionOfPartner(getUrl(PartnerRelTypes.GET_ALL_PARTNERS)),
                    (response) -> {
                        this.currentPartnerData = Collections.EMPTY_LIST;
                        this.cursorPartnerData = 0;
                    }
            );
        } else {
            throw new IllegalStateException();
        }
    }

    public void getSinglePartner() throws IOException {
        // TODO: theoretically here should also be checked,
        // if the location header is actually points the Partner type
        if (isLocationHeaderAvailable()) {
            getSinglePartner(getLocationHeaderURL());
        } else if (!this.currentPartnerData.isEmpty()) {
            getSinglePartner(this.cursorPartnerData);
        } else {
            throw new IllegalStateException();
        }
    }
    private void getSinglePartner(int index) throws IOException {
        getSinglePartner(this.currentPartnerData.get(index).getSelfLink().getUrl());
    }
    private void getSinglePartner(String url) throws IOException {
        processResponse(
                this.partnerWebClient.getSinglePartner(url),
                (response) -> {
                    this.currentPartnerData = new LinkedList(response.getResponseData());
                    this.cursorPartnerData = 0;
                }
        );
    }



    public boolean isCreatePartnerAllowed() {
        return isLinkAvailable(PartnerRelTypes.CREATE_PARTNER);
    }
    public void createPartner(PartnerModel person) throws IOException {
        if (isCreatePartnerAllowed()) {
            processResponse(
                    this.partnerWebClient.postNewPartner(getUrl(PartnerRelTypes.CREATE_PARTNER), person),
                    (response) -> {
                        this.currentPartnerData = Collections.EMPTY_LIST;
                        this.cursorPartnerData = 0;
                    }
            );
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isDeletePartnerAllowed() {
        return isLinkAvailable(PartnerRelTypes.DELETE_SINGLE_PARTNER);
    }
    public void deletePartner() throws IOException {
        if (isDeletePartnerAllowed()) {
            processResponse(
                    this.partnerWebClient.deletePartner(getUrl(PartnerRelTypes.DELETE_SINGLE_PARTNER)),
                    (response) -> {
                        this.currentPartnerData = Collections.EMPTY_LIST;
                        this.cursorPartnerData = 0;
                    }
        );
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isUpdatePartnerAllowed() {
        return isLinkAvailable(PartnerRelTypes.UPDATE_SINGLE_PARTNER);
    }
    public void updatePartner(PartnerModel partner) throws IOException {
        if (isUpdatePartnerAllowed()) {
            processResponse(
                    this.partnerWebClient.putNewPartner(
                            getUrl(PartnerRelTypes.UPDATE_SINGLE_PARTNER),
                            partner
                    ),
                    (response) -> {
                        this.currentPartnerData = Collections.EMPTY_LIST;
                        this.cursorPartnerData = 0;
                    }
            );
        } else {
            throw new IllegalStateException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Module">
    public boolean isGetAllModulesAllowed() {
        return isLinkAvailable(ModuleOfPartnerRelTypes.GET_ALL_MODULES_OF_PARTNER);
    }
    public void getAllModules() throws IOException {
        if (isGetAllModulesAllowed()) {
            processResponse(
                    this.moduleWebClient.getCollectionOfModule(getUrl(ModuleOfPartnerRelTypes.GET_ALL_MODULES_OF_PARTNER)),
                    (response) -> {
                        this.currentModuleData = Collections.EMPTY_LIST;
                        this.cursorModuleData = 0;
                    }
            );
        } else {
            throw new IllegalStateException();
        }
    }

    public void getSingleModule() throws IOException {
        // TODO: theoretically here should also be checked,
        // if the location header is actually points the Module type
        if (isLocationHeaderAvailable()) {
            getSingleModule(getLocationHeaderURL());
        } else if (!this.currentModuleData.isEmpty()) {
            getSingleModule(this.cursorModuleData);
        } else {
            throw new IllegalStateException();
        }
    }
    private void getSingleModule(int index) throws IOException {
        getSingleModule(this.currentModuleData.get(index).getSelfLink().getUrl());
    }
    private void getSingleModule(String url) throws IOException {
        processResponse(
                this.moduleWebClient.getSingleModule(url),
                (response) -> {
                    this.currentModuleData = new LinkedList(response.getResponseData());
                    this.cursorModuleData = 0;
                }
        );
    }



    public boolean isCreateModuleAllowed() {
        return isLinkAvailable(ModuleOfPartnerRelTypes.CREATE_MODULE);
    }
    public void createModule(ModuleModel person) throws IOException {
        if (isCreateModuleAllowed()) {
            processResponse(
                    this.moduleWebClient.postNewModule(getUrl(ModuleOfPartnerRelTypes.CREATE_MODULE), person),
                    (response) -> {
                        this.currentModuleData = Collections.EMPTY_LIST;
                        this.cursorModuleData = 0;
                    }
            );
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isDeleteModuleAllowed() {
        return isLinkAvailable(ModuleOfPartnerRelTypes.DELETE_SINGLE_MODULE_OF_PARTNER);
    }
    public void deleteModule() throws IOException {
        if (isDeleteModuleAllowed()) {
            processResponse(
                    this.moduleWebClient.deleteModule(getUrl(ModuleOfPartnerRelTypes.DELETE_SINGLE_MODULE_OF_PARTNER)),
                    (response) -> {
                        this.currentModuleData = Collections.EMPTY_LIST;
                        this.cursorModuleData = 0;
                    }
            );
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isUpdateModuleAllowed() {
        return isLinkAvailable(ModuleOfPartnerRelTypes.UPDATE_SINGLE_MODULE_OF_PARTNER);
    }
    public void updateModule(ModuleModel partner) throws IOException {
        if (isUpdateModuleAllowed()) {
            processResponse(
                    this.moduleWebClient.putNewModule(
                            getUrl(ModuleOfPartnerRelTypes.UPDATE_SINGLE_MODULE_OF_PARTNER),
                            partner
                    ),
                    (response) -> {
                        this.currentModuleData = Collections.EMPTY_LIST;
                        this.cursorModuleData = 0;
                    }
            );
        } else {
            throw new IllegalStateException();
        }
    }
    //</editor-fold>

}
