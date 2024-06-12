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

public class TestManagerAppIT {
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
        public void ok() {
            assertEquals(200, client.getLastStatusCode());
        }

        @Test
        public void get_all_partners_allowed() {
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
            public void ok() {
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
                public void ok() {
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
                    public void get_single_module_of_partner_disallowed() {
                        assertFalse(client.isGetSingleModuleOfPartnerAllowed());
                    }

                    @Test
                    public void module_of_partner_data_collection_empty() {
                        assertTrue(client.moduleOfPartnerData().isEmpty());
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
                        public void ok() {
                            assertEquals(201, client.getLastStatusCode());
                        }

                        @Test
                        public void get_module_of_partner_allowed() {
                            assertTrue(client.isGetSingleModuleOfPartnerAllowed());
                        }

                        @Test
                        public void get_single_partner_allowed() {
                            assertTrue(client.isGetSinglePartnerAllowed());
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
                            public void ok() {
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
                                public void ok() {
                                    assertEquals(204, client.getLastStatusCode());
                                }

                                @Test
                                public void is_get_all_modules_of_partners_allowed() {
                                    assertTrue(client.isGetAllModulesOfPartnerAllowed());
                                }
                                //</editor-fold>

                                @Nested
                                class GetAllModulesOfPartner {
                                    //<editor-fold desc="GetAllModulesOfPartner">
                                    @BeforeEach()
                                    public void setup() throws IOException {
                                        client.getAllModulesOfPartner();
                                    }

                                    @Test
                                    public void module_of_partner_collection_empty() {
                                        assertTrue(client.moduleOfPartnerData().isEmpty());
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
                                public void ok() {
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

                                @Nested
                                class GetSingleModuleOfPartner {
                                    @BeforeEach()
                                    public void setup() throws IOException {
                                        client.setModuleCursor(0);
                                        client.getSingleModuleOfPartner();
                                    }

                                    @Test
                                    public void ok() {
                                        assertEquals(200, client.getLastStatusCode());
                                    }
                                }
                            }

                            @Nested
                            class UpdateModuleOfPartner {
                                //<editor-fold desc="UpdatePartner">
                                String oldName;
                                String newName;
                                @BeforeEach()
                                public void setup() throws IOException {
                                    var module = client.moduleOfPartnerData().getFirst();
                                    oldName = module.getName();
                                    newName = "Some Special Non Existing Module \uD83E\uDDC0";
                                    module.setName(newName);
                                    client.updateModuleOfPartner(module);
                                }

                                @Test
                                public void ok() {
                                    assertEquals(204, client.getLastStatusCode());
                                }

                                @Test
                                public void get_module_of_partner_allowed() {
                                    assertTrue(client.isGetSingleModuleOfPartnerAllowed());
                                }
                                //</editor-fold>

                                @Nested
                                class GetModuleOfPartnerUpdated {
                                    //<editor-fold desc="GetPartnerUpdated">
                                    @BeforeEach()
                                    public void setup() throws IOException {
                                        client.getSingleModuleOfPartner();
                                    }

                                    @Test
                                    public void module_of_partner_updated() {
                                        assertEquals(newName, client.moduleOfPartnerData().getFirst().getName());
                                    }
                                    //</editor-fold>
                                }
                            }
                        }
                    }

                    @Nested
                    class TestCreatePartnerInvalidNumberOfSendStudents {
                        //<editor-fold desc="TestCreatePartnerInvalidNumberOfSendStudents">
                        @BeforeEach()
                        public void setup() throws IOException {
                            var sampleModule = getSampleModuleOfPartner();
                            sampleModule.setNumberOfCreditPoints(-1);
                            client.createModuleOfPartner(sampleModule);
                        }

                        @Test
                        public void client_error() {
                            assertEquals(400, client.getLastStatusCode());
                        }
                        //</editor-fold>
                    }
                }

                @Nested
                class UpdatePartner {
                    //<editor-fold desc="UpdatePartner">
                    String oldName;
                    String newName;
                    String newCountry;
                    @BeforeEach()
                    public void setup() throws IOException {
                        var partner = client.partnerData().getFirst();
                        oldName = partner.getName();
                        newName = "Some Special Non Existing University \uD83E\uDDC0";
                        newCountry = "Some non exiting country";
                        partner.setName(newName);
                        partner.setCountry(newCountry);
                        client.updatePartner(partner);
                    }

                    @Test
                    public void ok() {
                        assertEquals(204, client.getLastStatusCode());
                    }

                    @Test
                    public void get_single_partner_allowed() {
                        assertTrue(client.isGetSinglePartnerAllowed());
                    }
                    //</editor-fold>

                    @Nested
                    class GetPartnerUpdated {
                        //<editor-fold desc="GetPartnerUpdated">
                        @BeforeEach()
                        public void setup() throws IOException {
                            client.getSinglePartner();
                        }

                        @Test
                        public void partner_updated() {
                            assertEquals(newName, client.partnerData().getFirst().getName());
                        }
                        //</editor-fold>

                        @Nested
                        class GetAllPartner {
                            @BeforeEach
                            public void setup() throws IOException {
                                client.getAllPartners();
                            }

                            @Test
                            public void filter_partner_allowed() {
                                assertTrue(client.isGetAllPartnersByNameAndCountryAllowed());
                            }

                            @Test
                            public void shouldContain20Entries() {
                                assertEquals(20, client.partnerData().size());
                            }

                            @Nested
                            class GetAllPartnerFiltered {
                                @BeforeEach
                                public void setup() throws IOException {
                                    client.getAllPartnersByNameAndCountry("", newCountry);
                                }

                                @Test
                                public void ok() {
                                    assertEquals(200, client.getLastStatusCode());
                                }

                                @Test
                                public void shouldContain1Entries() {
                                    assertEquals(1, client.partnerData().size());
                                }
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
                assertTrue(client.partnerData().isEmpty());
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
                public void ok() {
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
                    public void ok() {
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
                        public void ok() {
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
                                assertTrue(client.partnerData().isEmpty());
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
                        public void ok() {
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

            @Nested
            class TestCreatePartnerInvalidNumberOfSendStudents {
                //<editor-fold desc="TestCreatePartnerInvalidNumberOfSendStudents">
                @BeforeEach()
                public void setup() throws IOException {
                    var samplePartner = getSamplePartner();
                    samplePartner.setNumberOfSendableStudents(-1);
                    client.createPartner(samplePartner);
                }

                @Test
                public void client_error() {
                    assertEquals(400, client.getLastStatusCode());
                }
                //</editor-fold>
            }

            @Nested
            class TestCreatePartnerInvalidNumberOfAcceptStudents {
                //<editor-fold desc="TestCreatePartnerInvalidNumberOfAcceptStudents">
                @BeforeEach()
                public void setup() throws IOException {
                    var samplePartner = getSamplePartner();
                    samplePartner.setNumberOfAcceptableStudents(-1);
                    client.createPartner(samplePartner);
                }

                @Test
                public void client_error() {
                    assertEquals(400, client.getLastStatusCode());
                }
                //</editor-fold>
            }
        }

        @Nested
        class CreatePartnerOrdering {
            String name1 = "Ich will nicht mehr. \uD83D\uDE42\uD83D\uDE43\uD83D\uDE42\uD83D\uDD2B";
            String name2 = "ABC";
            @BeforeEach()
            public void setup() throws IOException {
                var extraClient1 = new ManagerRestClient();
                extraClient1.start();
                extraClient1.getAllPartners();
                var person1 = getSamplePartner();
                person1.setName(name1);
                extraClient1.createPartner(person1);

                var extraClient2 = new ManagerRestClient();
                extraClient2.start();
                extraClient2.getAllPartners();
                var person2 = getSamplePartner();
                person2.setName(name2);
                extraClient2.createPartner(person2);

                client.getAllPartners();
            }

            @Test
            public void ok() {
                assertEquals(200, client.getLastStatusCode());
            }

            @Test
            public void ascAllowed() {
                assertTrue(client.isGetAllPartnersByNameAndCountryAllowedAsc());
            }
            @Test
            public void descAllowed() {
                assertTrue(client.isGetAllPartnersByNameAndCountryAllowedDesc());
            }

            @Nested
            class Asc {
                @BeforeEach()
                public void setup() throws IOException {
                    client.getAllPartnersByNameAndCountryAsc("", "");
                }

                @Test
                public void name2ShouldBeFirst() {
                    assertEquals(name2, client.partnerData().getFirst().getName());
                }
                @Test
                public void name1ShouldBeSecond() {
                    assertEquals(name1, client.partnerData().get(1).getName());
                }
            }

            @Nested
            class Desc {
                @BeforeEach()
                public void setup() throws IOException {
                    client.getAllPartnersByNameAndCountryDsc("", "");
                }

                @Test
                public void name1ShouldBeFirst() {
                    assertEquals(name1, client.partnerData().getFirst().getName());
                }
                @Test
                public void name2ShouldBeSecond() {
                    assertEquals(name2, client.partnerData().get(1).getName());
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
        module.setSemesterType(2);
        module.setNumberOfCreditPoints(5);
        return module;
    }
    //</editor-fold>
}
