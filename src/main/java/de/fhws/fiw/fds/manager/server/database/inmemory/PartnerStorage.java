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

    @Override
    public void resetDatabase() {
        this.storage.clear();
    }

    public PartnerStorage() {
        super();
    }

    private Predicate<Partner> byPartnerNameAndCountry(String partnerName, String country) {
        return p -> (partnerName.isEmpty() || p.getName().toLowerCase().contains(partnerName.toLowerCase()))
                && (country.isEmpty() || p.getCountry().toLowerCase().contains(country.toLowerCase()));
    }
}
