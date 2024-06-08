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

public class ManagerRestClient extends AbstractRestClient {
    private static final String BASE_URL = Constants.BASE_URL;

    final private DispatcherWebClient dispatcherWebClient;
    final private PartnerWebClient partnerWebClient;
    final private ModuleWebClient moduleWebClient;

    private List<PartnerModel> currentPartnerData;
    private int cursorPartnerData = 0;
    public void setPartnerCursor(int index) {
        if (0 <= index && index < this.currentPartnerData.size()) {
            this.cursorPartnerData = index;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private List<ModuleModel> currentModuleData;
    private int cursorModuleData = 0;
    public void setModuleCursor(int index) {
        if (0 <= index && index < this.currentModuleData.size()) {
            this.cursorModuleData = index;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public ManagerRestClient() {
        super();
        this.dispatcherWebClient = new DispatcherWebClient();
        this.partnerWebClient = new PartnerWebClient();
        this.moduleWebClient = new ModuleWebClient();
    }

    public void start() throws IOException {
        processResponse(
                this.dispatcherWebClient.getDispatcher(BASE_URL),
                (response -> {})
        );
    }

    public void resetDatabase() throws IOException {
        processResponse(
                this.dispatcherWebClient.resetDatabaseOnServer(BASE_URL),
                (response) -> {}
        );
    }

    public boolean hasNextPage() {
        return isLinkAvailable("next");
    }

    public boolean hasPrevPage() {
        return isLinkAvailable("prev");
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
                        this.currentPartnerData = new LinkedList<>(response.getResponseData());
                        this.cursorPartnerData = 0;
                    }
            );
        } else {
            throw new IllegalStateException();
        }
    }
    public void getNextPartnerPage() throws IOException {
        // TODO: theoretically here should also be checked,
        //  if the location header is actually points the Partner type
        if (hasNextPage()) {
            processResponse(
                    this.partnerWebClient.getCollectionOfPartner(getUrl("next")),
                    (response) -> {
                        this.currentPartnerData = new LinkedList<>(response.getResponseData());
                        this.cursorPartnerData = 0;
                    }
            );
        } else {
            throw  new IllegalStateException();
        }
    }
    public void getPrevPartnerPage() throws IOException {
        // TODO: theoretically here should also be checked,
        //  if the location header is actually points the Partner type
        if (hasPrevPage()) {
            processResponse(
                    this.partnerWebClient.getCollectionOfPartner(getUrl("prev")),
                    (response) -> {
                        this.currentPartnerData = new LinkedList<>(response.getResponseData());
                        this.cursorPartnerData = 0;
                    }
            );
        } else {
            throw  new IllegalStateException();
        }
    }
    public List<PartnerModel> partnerData() {
        if (this.currentPartnerData.isEmpty()) {
            throw new IllegalStateException();
        }
        return this.currentPartnerData;
    }

    public boolean isGetSinglePartnerAllowed() {
        return !this.currentPartnerData.isEmpty() || isLocationHeaderAvailable();
    }

    public void getSinglePartner() throws IOException {
        // TODO: theoretically here should also be checked,
        //  if the location header is actually points the Partner type
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
                    this.currentModuleData = Collections.EMPTY_LIST;
                    this.cursorModuleData = 0;
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
    public boolean isGetAllModulesOfPartnerAllowed() {
        return isLinkAvailable(ModuleOfPartnerRelTypes.GET_ALL_MODULES_OF_PARTNER) || !this.currentPartnerData.isEmpty();
    }
    public void getAllModulesOfPartner() throws IOException {
        if (isLinkAvailable(ModuleOfPartnerRelTypes.GET_ALL_MODULES_OF_PARTNER)) {
            processResponse(
                    this.moduleWebClient.getCollectionOfModule(getUrl(ModuleOfPartnerRelTypes.GET_ALL_MODULES_OF_PARTNER)),
                    (response) -> {
                        this.currentModuleData = new LinkedList<>(response.getResponseData());
                        this.cursorModuleData = 0;
                    }
            );
        } else if (!this.currentPartnerData.isEmpty()) {
            processResponse(
                    this.moduleWebClient.getCollectionOfModule(
                            this.currentPartnerData.get(this.cursorPartnerData).getModules().getUrl()
                    ),
                    (response) -> {
                        this.currentPartnerData = Collections.EMPTY_LIST;
                        this.cursorPartnerData = 0;
                        this.currentModuleData = new LinkedList<>(response.getResponseData());
                        this.cursorModuleData = 0;
                    }
            );
        } else {
            throw new IllegalStateException();
        }
    }
    public void getNextModuleOfPartnerPage() throws IOException {
        // TODO: theoretically here should also be checked,
        //  if the location header is actually points the Partner type
        if (hasNextPage()) {
            processResponse(
                    this.moduleWebClient.getCollectionOfModule(getUrl("next")),
                    (response) -> {
                        this.currentModuleData = new LinkedList<>(response.getResponseData());
                        this.cursorModuleData = 0;
                    }
            );
        } else {
            throw  new IllegalStateException();
        }
    }
    public void getPrevModuleOfPartnerPage() throws IOException {
        // TODO: theoretically here should also be checked,
        //  if the location header is actually points the Partner type
        if (hasPrevPage()) {
            processResponse(
                    this.moduleWebClient.getCollectionOfModule(getUrl("prev")),
                    (response) -> {
                        this.currentModuleData = new LinkedList<>(response.getResponseData());
                        this.cursorModuleData = 0;
                    }
            );
        } else {
            throw  new IllegalStateException();
        }
    }
    public List<ModuleModel> moduleOfPartnerData() {
        if (this.currentModuleData.isEmpty()) {
            throw new IllegalStateException();
        }
        return this.currentModuleData;
    }

    public boolean isGetSingleModuleOfPartnerAllowed() {
        return !this.currentModuleData.isEmpty() || isLocationHeaderAvailable();
    }
    public void getSingleModuleOfPartner() throws IOException {
        // TODO: theoretically here should also be checked,
        //  if the location header is actually points the Module type
        if (isLocationHeaderAvailable()) {
            getSingleModuleOfPartner(getLocationHeaderURL());
        } else if (!this.currentModuleData.isEmpty()) {
            getSingleModuleOfPartner(this.cursorModuleData);
        } else {
            throw new IllegalStateException();
        }
    }
    private void getSingleModuleOfPartner(int index) throws IOException {
        getSingleModuleOfPartner(this.currentModuleData.get(index).getSelfLink().getUrl());
    }
    private void getSingleModuleOfPartner(String url) throws IOException {
        processResponse(
                this.moduleWebClient.getSingleModule(url),
                (response) -> {
                    this.currentModuleData = new LinkedList(response.getResponseData());
                    this.cursorModuleData = 0;
                }
        );
    }



    public boolean isCreateModuleOfPartnerAllowed() {
        return isLinkAvailable(ModuleOfPartnerRelTypes.CREATE_MODULE);
    }
    public void createModuleOfPartner(ModuleModel person) throws IOException {
        if (isCreateModuleOfPartnerAllowed()) {
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

    public boolean isDeleteModuleOfPartnerAllowed() {
        return isLinkAvailable(ModuleOfPartnerRelTypes.DELETE_SINGLE_MODULE_OF_PARTNER);
    }
    public void deleteModuleOfPartner() throws IOException {
        if (isDeleteModuleOfPartnerAllowed()) {
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

    public boolean isUpdateModuleOfPartnerAllowed() {
        return isLinkAvailable(ModuleOfPartnerRelTypes.UPDATE_SINGLE_MODULE_OF_PARTNER);
    }
    public void updateModuleOfPartner(ModuleModel partner) throws IOException {
        if (isUpdateModuleOfPartnerAllowed()) {
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
