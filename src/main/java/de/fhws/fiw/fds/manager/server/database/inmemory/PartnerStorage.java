package de.fhws.fiw.fds.manager.server.database.inmemory;

import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.manager.server.database.PartnerDao;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import org.apache.commons.lang.ObjectUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PartnerStorage extends AbstractInMemoryStorage<Partner> implements PartnerDao {

    // Copied from parent to implement ordering, because sub functions are private
    @Override
    public CollectionModelResult<Partner> readByPartnerNameAndCountry(String partnerName, String country, SearchParameter searchParameter) {
        final CollectionModelResult<Partner> filteredResult =
                new CollectionModelResult<>(filterBy(byPartnerNameAndCountry(partnerName, country)));
        final CollectionModelResult<Partner> orderedResult =
                new CollectionModelResult<>(
                        orderBy(
                                filteredResult.getResult(),
                                (a, b) -> {
                                    if (searchParameter.getOrderByAttribute().equalsIgnoreCase("asc"))
                                        return a.getName().compareToIgnoreCase(b.getName());
                                    else if (searchParameter.getOrderByAttribute().equalsIgnoreCase("desc"))
                                        return b.getName().compareToIgnoreCase(a.getName());
                                    else
                                        return 0;
                                }
                        )
                );
        final CollectionModelResult<Partner> page = InMemoryPaging.page(orderedResult,
                searchParameter.getOffset(), searchParameter.getSize());
        final CollectionModelResult<Partner> returnValue =
                new CollectionModelResult<>(clone(page.getResult()));
        returnValue.setTotalNumberOfResult(filteredResult.getTotalNumberOfResult());

        return returnValue;
    }

    private Collection<Partner> filterBy(final Predicate<Partner> predicate) {
        return this.storage.values().stream().filter(predicate).collect(Collectors.toList());
    }

    private Collection<Partner> orderBy(final Collection<Partner> result, final Comparator<Partner> comparator) {
        return result.stream().sorted(comparator).toList();
    }

    private Collection<Partner> clone(final Collection<Partner> result) {
        return result.stream().map(e -> clone(e)).collect(Collectors.toList());
    }

    private Partner clone(final Partner result) {
        final Partner clone = (Partner) ObjectUtils.cloneIfPossible(result);
        return clone;
    }

    @Override
    public void resetDatabase() {
        this.storage.clear();
    }

    private Predicate<Partner> byPartnerNameAndCountry(String partnerName, String country) {
        return p -> (partnerName.isEmpty() || p.getName().toLowerCase().contains(partnerName.toLowerCase()))
                && (country.isEmpty() || p.getCountry().toLowerCase().contains(country.toLowerCase()));
    }
}
