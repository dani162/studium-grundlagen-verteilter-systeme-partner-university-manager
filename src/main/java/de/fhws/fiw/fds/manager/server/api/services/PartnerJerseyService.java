package de.fhws.fiw.fds.manager.server.api.services;

import de.fhws.fiw.fds.manager.server.api.models.Module;
import de.fhws.fiw.fds.manager.server.api.models.Partner;
import de.fhws.fiw.fds.manager.server.api.queries.QueryByPartnerNameAndCountry;
import de.fhws.fiw.fds.manager.server.api.queries.QueryModuleOfPartner;
import de.fhws.fiw.fds.manager.server.api.states.partner.*;
import de.fhws.fiw.fds.manager.server.api.states.partner_modules.*;
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
    @Produces({MediaType.APPLICATION_JSON})
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
    @Produces({MediaType.APPLICATION_JSON})
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
    @Produces({MediaType.APPLICATION_JSON})
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
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteSinglePartner(@PathParam("id") final long id) {
        try {
            return new DeleteSinglePartner(this.serviceContext, id).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    // ModuleOfPartner
    @GET
    @Path("{partnerId:\\d+}/modules")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getModuleOfPartner(
            @PathParam("partnerId") final long partnerId,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("20") @QueryParam("size") int size
    ) {
        try {
            return new GetAllModulesOfPartner(
                    this.serviceContext, partnerId,
                    new QueryModuleOfPartner<>(partnerId, offset, size)
            ).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(
                    Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build()
            );
        }
    }

    @GET
    @Path("{partnerId:\\d+}/modules/{moduleId:\\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getSingleModuleOfPartner(
            @PathParam("partnerId") final long partnerId,
            @PathParam("moduleId") final long moduleId
    ) {
        try {
            return new GetSingleModuleOfPartner(this.serviceContext, partnerId, moduleId).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response
                    .status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build()
            );
        }
    }

    @POST
    @Path("{partnerId:\\d+}/modules")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createSingleModuleOfPartner(
            @PathParam("partnerId") final long partnerId,
            final Module module
    ) {
        try {
            return new PostNewModuleOfPartner(this.serviceContext, partnerId, module).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response
                    .status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build()
            );
        }
    }

    @PUT
    @Path("{partnerId:\\d+}/modules/{moduleId:\\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateSingleModuleOfPartner(
            @PathParam("partnerId") final long partnerId,
            @PathParam("moduleId") final long moduleId,
            final Module module
    ) {
        try {
            return new PutSingleModuleOfPartner(this.serviceContext, partnerId, moduleId, module).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @DELETE
    @Path("{partnerId:\\d+}/modules/{moduleId:\\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteSingleModuleOfPartner(
            @PathParam("partnerId") final long partnerId,
            @PathParam("moduleId") final long moduleId
    ) {
        try {
            return new DeleteSingleModuleOfPartner(this.serviceContext, moduleId, partnerId).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }
}
