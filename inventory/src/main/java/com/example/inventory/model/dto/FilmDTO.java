package com.example.inventory.model.dto;

import java.util.Collection;

public class FilmDTO {
    
    private Long id;

    private String title;

    private Integer year;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public static FilmDTO findByCodeIsIn(Collection<FilmDTO> listFilms, Long id) {
        return listFilms.stream().filter(film -> id.equals(film.getId())).findFirst().orElse(null);
    }

}
