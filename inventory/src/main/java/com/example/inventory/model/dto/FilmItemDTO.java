package com.example.inventory.model.dto;

import com.example.inventory.model.FilmItem;

public class FilmItemDTO {
    
    private Long id;

    private FilmDTO film;

    public FilmItemDTO(){}

    public FilmItemDTO(FilmItem item){
        this.id = item.getId();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FilmDTO getFilm() {
        return this.film;
    }

    public void setFilm(FilmDTO film) {
        this.film = film;
    }

}
