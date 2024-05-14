package de.fhws.fiw.fds.manager.server.database;

import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public interface PartnerDao extends IDatabaseAccessObject<Partner> {
    CollectionModelResult<Partner> readByPartnerNameAndCountry(String partnerName, String country, SearchParameter searchParameter);
}
