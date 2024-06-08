package de.fhws.fiw.fds.sutton.server;

import com.github.javafaker.Faker;
import de.fhws.fiw.fds.manager.client.models.ModuleModel;
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
    class Dispatcher {

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
        class GetAllPartners {
            //<editor-fold desc="GetAllPartners">
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

            @Test
            public void next_page_allowed() {
                assertTrue(client.hasNextPage());
            }
            @Test
            public void prev_page_disallowed() {
                assertFalse(client.hasPrevPage());
            }
            //</editor-fold>

            @Nested
            class GetNextPartnerPage1 {
                //<editor-fold desc="GetNextPartnerPage1">
                @BeforeEach()
                public void setup() throws IOException {
                    client.getNextPartnerPage();
                }

                @Test
                public void next_page_allowed() {
                    assertTrue(client.hasNextPage());
                }
                @Test
                public void prev_page_disallowed() {
                    assertTrue(client.hasPrevPage());
                }
                //</editor-fold>

                @Nested
                class GetNextPartnerPage2 {
                    //<editor-fold desc="GetNextPartnerPage2">
                    @BeforeEach()
                    public void setup() throws IOException {
                        client.getNextPartnerPage();
                    }

                    @Test
                    public void next_page_allowed() {
                        assertFalse(client.hasNextPage());
                    }
                    @Test
                    public void prev_page_disallowed() {
                        assertTrue(client.hasPrevPage());
                    }
                    //</editor-fold>


                    @Nested
                    class GetPrevPartnerPage1 {
                        //<editor-fold desc="GetPrevPartnerPage1">
                        @BeforeEach()
                        public void setup() throws IOException {
                            client.getPrevPartnerPage();
                        }

                        @Test
                        public void next_page_allowed() {
                            assertTrue(client.hasNextPage());
                        }
                        @Test
                        public void prev_page_disallowed() {
                            assertTrue(client.hasPrevPage());
                        }
                        //</editor-fold>

                        @Nested
                        class GetPrevPartnerPage0 {
                            //<editor-fold desc="GetPrevPartnerPage0">
                            @BeforeEach()
                            public void setup() throws IOException {
                                client.getPrevPartnerPage();
                            }

                            @Test
                            public void next_page_allowed() {
                                assertTrue(client.hasNextPage());
                            }
                            @Test
                            public void prev_page_disallowed() {
                                assertFalse(client.hasPrevPage());
                            }
                            //</editor-fold>
                        }
                    }
                }
            }

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

                @Test
                public void get_modules_allowed() {
                    assertTrue(client.isGetAllModulesOfPartnerAllowed());
                }
                //</editor-fold>

                @Nested
                class GetAllModulesOfPartnerEmpty {
                    //<editor-fold desc="TestGetAllModulesOfPartnerEmpty">
                    @BeforeEach()
                    public void setup() throws IOException {
                        client.getAllModulesOfPartner();
                    }

                    @Test
                    public void get_partner_disallowed() {
                        assertFalse(client.isGetSingleModuleOfPartnerAllowed());
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
                    class CreateModuleOfPartner {
                        //<editor-fold desc="CreateModuleOfPartner">
                        public ModuleModel createdSampleModuleOfPartner = getSampleModuleOfPartner();

                        @BeforeEach()
                        public void setup() throws IOException {
                            client.createModuleOfPartner(createdSampleModuleOfPartner);
                        }

                        @Test
                        public void available() {
                            assertEquals(201, client.getLastStatusCode());
                        }

                        @Test
                        public void get_module_of_partner_allowed() {
                            assertTrue(client.isGetSingleModuleOfPartnerAllowed());
                        }
                        //</editor-fold>

                        @Nested
                        class GetModuleOfPartner {
                            //<editor-fold desc="GetModuleOfPartner">
                            @BeforeEach()
                            public void setup() throws IOException {
                                client.getSingleModuleOfPartner();
                            }

                            @Test
                            public void available() {
                                assertEquals(200, client.getLastStatusCode());
                            }

                            @Test
                            public void delete_module_of_partner_allowed() {
                                assertTrue(client.isDeleteModuleOfPartnerAllowed());
                            }

                            @Test
                            public void get_module_of_partner_equals_created_module() {
                                assertNotSame(client.moduleOfPartnerData().getFirst(), createdSampleModuleOfPartner);
                                assertEquals(client.moduleOfPartnerData().getFirst(), createdSampleModuleOfPartner);
                            }
                            //</editor-fold>

                            @Nested
                            class DeleteModuleOfPartner {
                                //<editor-fold desc="DeleteModuleOfPartner">
                                @BeforeEach()
                                public void setup() throws IOException {
                                    client.deleteModuleOfPartner();
                                }

                                @Test
                                public void available() {
                                    assertEquals(204, client.getLastStatusCode());
                                }

                                @Test
                                public void is_get_all_partners_allowed() {
                                    assertTrue(client.isGetAllModulesOfPartnerAllowed());
                                }
                                //</editor-fold>

                                @Nested
                                class TestGetAllModulesOfPartner {
                                    //<editor-fold desc="TestGetAllModulesOfPartner">
                                    @BeforeEach()
                                    public void setup() throws IOException {
                                        client.getAllModulesOfPartner();
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
                            class TestGetAllModulesOfPartner {
                                //<editor-fold desc="TestGetAllModulesOfPartner">
                                @BeforeEach()
                                public void setup() throws IOException {
                                    client.getAllModulesOfPartner();
                                }

                                @Test
                                public void available() {
                                    assertEquals(200, client.getLastStatusCode());
                                }

                                @Test
                                public void get_module_of_partner_allowed() {
                                    assertTrue(client.isGetSingleModuleOfPartnerAllowed());
                                }

                                @Test
                                public void module_of_partner_in_collection() {
                                    assertEquals(1, client.moduleOfPartnerData().size());
                                }
                                //</editor-fold>
                            }
                        }
                    }
                }

            }
        }

        @Nested
        class GetAllPartnersEmpty {
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

    public ModuleModel getSampleModuleOfPartner() {
        var module = new ModuleModel();
        module.setName(faker.job().field());
        module.setNumberOfCreditPoints(5);
        return module;
    }
    //</editor-fold>
}
