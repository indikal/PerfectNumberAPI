package lk.inli.rest.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The API end point definitions for Perfect Number service
 **/

@Path("/perfectnumber")
@Produces(MediaType.APPLICATION_JSON)
public interface PerfectNumberService {

    @GET
    @Path("{input}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isPerfectNumber(@PathParam("input") Long input);

    @GET
    public Response getAllPerfectNumbers(@QueryParam("start") Long start, @QueryParam("end") Long end);

    @GET
    @Path("ping")
    public Response heartbeat();
}
