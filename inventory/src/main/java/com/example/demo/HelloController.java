package com.example.demo;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.example.demo.dao.ServiceBean;

/**
 *
 */
@Path("/")
@Singleton
public class HelloController {


    @Inject
    private ServiceBean ejb;

    @GET
    @Path("/test")
    public String test() {
        
        return "Ok size "+(ejb.findAll().size());
    }

    @GET
    @Path("/hello")
    public String hello() {
        return "General Kenobi";
    }
}
