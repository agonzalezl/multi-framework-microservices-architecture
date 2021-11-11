package com.example.inventory;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 */
@Path("/")
@Singleton
public class HelloController {

    @GET
    @Path("/")
    public String hello() {
        return "Hello world!";
    }

}
