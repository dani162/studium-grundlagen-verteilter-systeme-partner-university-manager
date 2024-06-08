package de.fhws.fiw.fds.sutton.server;

import com.github.javafaker.Faker;
import de.fhws.fiw.fds.manager.client.models.PartnerModel;
import de.fhws.fiw.fds.manager.client.rest.ManagerRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestPartnerAppIT {
    final private Faker faker = new Faker();
    private ManagerRestClient client;

    @BeforeEach
    public void setup() throws IOException {
        this.client = new ManagerRestClient();
        this.client.resetDatabase();
    }

    @Nested
    class TestDispatcher {

        //<editor-fold desc="Dispatcher">
        @BeforeEach()
        public void setup() throws IOException {
            client.start();
        }

        @Test
        public void is_available() {
            assertEquals(200, client.getLastStatusCode());
        }

        @Test
        public void is_get_all_partners_allowed() {
            assertTrue(client.isGetAllPartnersAllowed());
        }
        //</editor-fold>

        @Nested
        class TestGetAllPartners {
            //<editor-fold desc="TestGetAllPartners">
            @BeforeEach()
            public void setup() throws IOException {
                addExamplePartners(56);
                client.getAllPartners();
            }

            @Test
            public void available() {
                assertEquals(200, client.getLastStatusCode());
            }

            @Test
            public void create_partner_allowed() {
                assertTrue(client.isCreatePartnerAllowed());
            }

            @Test
            public void get_partner_allowed() {
                assertTrue(client.isGetSinglePartnerAllowed());
            }
            //</editor-fold>

            @Nested
            class GetPartner {
                //<editor-fold desc="GetPartner">
                @BeforeEach()
                public void setup() throws IOException {
                    client.setPartnerCursor(0);
                    client.getSinglePartner();
                }

                @Test
                public void available() {
                    assertEquals(200, client.getLastStatusCode());
                }
                //</editor-fold>
            }
        }

        @Nested
        class TestGetAllPartnersEmpty {
            //<editor-fold desc="TestGetAllPartnersEmpty">
            @BeforeEach()
            public void setup() throws IOException {
                client.getAllPartners();
            }

            @Test
            public void get_partner_disallowed() {
                assertFalse(client.isGetSinglePartnerAllowed());
            }

            @Test
            public void partner_collection_empty() {
                assertThrows(
                        IllegalStateException.class,
                        () -> client.partnerData()
                );
            }
            //</editor-fold>

            @Nested
            class TestCreatePartner {
                //<editor-fold desc="TestCreatePartner">
                public PartnerModel createdSamplePartner = getSamplePartner();

                @BeforeEach()
                public void setup() throws IOException {
                    client.createPartner(createdSamplePartner);
                }

                @Test
                public void available() {
                    assertEquals(201, client.getLastStatusCode());
                }

                @Test
                public void get_partner_allowed() {
                    assertTrue(client.isGetSinglePartnerAllowed());
                }
                //</editor-fold>

                @Nested
                class GetPartner {
                    //<editor-fold desc="GetPartner">
                    @BeforeEach()
                    public void setup() throws IOException {
                        client.getSinglePartner();
                    }

                    @Test
                    public void available() {
                        assertEquals(200, client.getLastStatusCode());
                    }

                    @Test
                    public void get_partner_equals_created_partner() {
                        assertNotSame(client.partnerData().getFirst(), createdSamplePartner);
                        assertEquals(client.partnerData().getFirst(), createdSamplePartner);
                    }
                    //</editor-fold>

                    @Nested
                    class DeletePartner {
                        //<editor-fold desc="DeletePartner">
                        @BeforeEach()
                        public void setup() throws IOException {
                            client.deletePartner();
                        }

                        @Test
                        public void available() {
                            assertEquals(204, client.getLastStatusCode());
                        }

                        @Test
                        public void is_get_all_partners_allowed() {
                            assertTrue(client.isGetAllPartnersAllowed());
                        }
                        //</editor-fold>

                        @Nested
                        class TestGetAllPartners {
                            //<editor-fold desc="TestGetAllPartners">
                            @BeforeEach()
                            public void setup() throws IOException {
                                client.getAllPartners();
                            }

                            @Test
                            public void partner_collection_empty() {
                                assertThrows(
                                        IllegalStateException.class,
                                        () -> client.partnerData()
                                );
                            }
                            //</editor-fold>
                        }
                    }

                    @Nested
                    class TestGetAllPartners {
                        //<editor-fold desc="TestGetAllPartners">
                        @BeforeEach()
                        public void setup() throws IOException {
                            client.getAllPartners();
                        }

                        @Test
                        public void available() {
                            assertEquals(200, client.getLastStatusCode());
                        }

                        @Test
                        public void get_partner_allowed() {
                            assertTrue(client.isGetSinglePartnerAllowed());
                        }

                        @Test
                        public void partner_in_collection() {
                            assertEquals(1, client.partnerData().size());
                        }
                        //</editor-fold>
                    }
                }
            }
        }
    }

    //<editor-fold desc="Utils">
    public PartnerModel getSamplePartner() {
        var university = faker.university();
        var country = faker.country();
        var contact = faker.name();

        var partner = new PartnerModel();
        partner.setName(university.name());
        partner.setCountry(country.name());
        partner.setWebsite("https://" + university.name().replaceAll(" ", "-").toLowerCase() + "." + country.countryCode2() + "/");
        partner.setContactPerson(contact.name());
        partner.setCountry(faker.country().name());
        partner.setDepartment("Department of Information Technology");
        partner.setNumberOfAcceptableStudents(10);
        partner.setNumberOfAcceptableStudents(20);
        return partner;
    }

    public void addExamplePartners(int amount) throws IOException {
        for (int i = 0; i < amount; i++) {
            var extraClient = new ManagerRestClient();
            extraClient.start();
            extraClient.getAllPartners();
            extraClient.createPartner(getSamplePartner());
        }
    }
    //</editor-fold>
}
