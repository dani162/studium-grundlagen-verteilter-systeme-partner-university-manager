package de.fhws.fiw.fds.manager.server.api.services;

import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.manager.server.api.queries.QueryByPartnerNameAndCountry;
import de.fhws.fiw.fds.manager.server.api.states.partner.*;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(PartnerUri.PATH_ELEMENT)
public class PartnerJerseyService extends AbstractJerseyService {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPartners(
            @DefaultValue("") @QueryParam("name") final String partnerName,
            @DefaultValue("") @QueryParam("country") final String country,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("20") @QueryParam("size") int size
    ) {
        try {
            return new GetAllPartners(this.serviceContext,
                    new QueryByPartnerNameAndCountry<>(partnerName, country, offset, size)).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(e.getExceptionMessage(), e.getStatus().getCode());
        }
    }

    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSinglePartner(@PathParam("id") final long id) {
        try {
            return new GetSinglePartner(this.serviceContext, id).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response
                    .status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build()
            );
        }
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createSinglePartner(final Partner partnerModel) {
        try {
            return new PostNewPartner(this.serviceContext, partnerModel).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }
    
    @PUT
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateSinglePartner(@PathParam("id") final long id, final Partner partnerModel) {
        try {
            return new PutSinglePartner(this.serviceContext, id, partnerModel).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @DELETE
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteSinglePartner(@PathParam("id") final long id) {
        try {
            return new DeleteSinglePartner(this.serviceContext, id).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }
    
}
