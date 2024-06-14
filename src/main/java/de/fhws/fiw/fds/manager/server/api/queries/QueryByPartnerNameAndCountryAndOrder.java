package de.fhws.fiw.fds.manager.server.api.queries;

import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.manager.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class QueryByPartnerNameAndCountryAndOrder<R> extends AbstractQuery<R, Partner> {
    private String partnerName;
    private String country;
    private String order;

    public QueryByPartnerNameAndCountryAndOrder(String partnerName, String country, String order, int offset, int size) {
        this.partnerName = partnerName;
        this.country = country;
        this.order = order;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
    }

    @Override
    protected CollectionModelResult<Partner> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        searchParameter.setOrderByAttribute(this.order);
        return DaoFactory.getInstance().getPartnerDao().readByPartnerNameAndCountry(
                this.partnerName,
                this.country,
                searchParameter
        );
    }

    //<editor-fold desc="getter & setter">
    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    //</editor-fold>
}
