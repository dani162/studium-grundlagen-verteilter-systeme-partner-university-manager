package de.fhws.fiw.fds.manager.server.database.inmemory;

import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.manager.server.database.PartnerDao;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import java.time.LocalDate;
import java.util.function.Predicate;

public class PartnerStorage extends AbstractInMemoryStorage<Partner> implements PartnerDao {
    @Override
    public CollectionModelResult<Partner> readByPartnerNameAndCountry(String partnerName, String country, SearchParameter searchParameter) {
        return InMemoryPaging.page(this.readAllByPredicate(
                byPartnerNameAndCountry(partnerName, country),
                searchParameter
        ), searchParameter.getOffset(), searchParameter.getSize());
    }

    public PartnerStorage() {
        super();
        this.populateData();
    }

    private void populateData() {
        create(new Partner(
                "Christ University",
                "India",
                "Deparment of Information Technology",
                "https://christuniversity.in/",
                "Dr. Ravi Kumar",
                20,
                20,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 7, 1)
        ));

        for (int i = 0; i < 100; i++) {
            create(new Partner(
                    "Sample " + i,
                    "Country " + i,
                    "Department " + i,
                    "url " + i,
                    "contact person " + i,
                    i,
                    i + 1,
                    LocalDate.of(2024, 1, 1),
                    LocalDate.of(2024, 7, 1)
            ));
        }
    }

    private Predicate<Partner> byPartnerNameAndCountry(String partnerName, String country) {
        return p -> (partnerName.isEmpty() || p.getName().toLowerCase().contains(partnerName.toLowerCase()))
                && (country.isEmpty() || p.getCountry().toLowerCase().contains(country.toLowerCase()));
    }
}
