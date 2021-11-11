package com.example.inventory.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.example.inventory.model.dto.FilmDTO;

@RegisterRestClient
@ApplicationScoped
public interface FilmClient {

    @GET
    @Path("/")
    @Produces("application/json")
    List<FilmDTO> getAll(@QueryParam("ids") String ids);

}
