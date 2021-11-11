package com.example.inventory.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.example.inventory.client.FilmClient;
import com.example.inventory.dao.FilmItemDao;
import com.example.inventory.model.FilmItem;
import com.example.inventory.model.dto.FilmDTO;
import com.example.inventory.model.dto.FilmItemDTO;

import org.eclipse.microprofile.rest.client.inject.RestClient;


@Singleton
public class FilmIntemService {
    
    @Inject
    @RestClient
    private FilmClient client;
    
    @Inject
    private FilmItemDao dao;

    public List<FilmItemDTO> getAll() {
        
        List<FilmItem> items = dao.findAll();
        
        System.out.println("Lets debug the database");
        System.out.println(items.size());

        Set<String> films_id_list = new HashSet<String>();

        for(FilmItem item : items){
            films_id_list.add(item.getFilmId().toString());
        }

        films_id_list.add("1");

        String ids = String.join(",", films_id_list);

        List<FilmDTO> films = client.getAll(ids);

        List<FilmItemDTO> list_item_dto = new ArrayList<>();
        for(FilmItem item : items){
            FilmItemDTO itemDTO = new FilmItemDTO(item);
            
            itemDTO.setFilm(FilmDTO.findByCodeIsIn(films, item.getFilmId()));

            list_item_dto.add(itemDTO);
        }


        return list_item_dto;
    }
    
}
