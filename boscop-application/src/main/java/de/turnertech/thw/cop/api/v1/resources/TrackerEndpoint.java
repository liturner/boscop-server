package de.turnertech.thw.cop.api.v1.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/tracker")
public class TrackerEndpoint {

    @GET
    public String getAllTrackers() {
        return "Hello, JAX-RS!";
    }

}
