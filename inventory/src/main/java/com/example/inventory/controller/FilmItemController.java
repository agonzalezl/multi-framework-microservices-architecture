package com.example.inventory.controller;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.example.inventory.model.dto.FilmItemDTO;
import com.example.inventory.service.FilmIntemService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;

@Path("/item")
@Singleton
public class FilmItemController {
    
    @Inject
    private FilmIntemService service;

    @GET
    @Path("/")
    @Produces("application/json")
    public List<FilmItemDTO> getAll() {
        return service.getAll();
    }

}
