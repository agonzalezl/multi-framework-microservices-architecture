package com.example.inventory.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.inventory.model.FilmItem;

@Stateless
public class  FilmItemDao   {
    @PersistenceContext
    private EntityManager em;
    public void put(FilmItem p){
        em.persist(p);
    }
    public void delete(FilmItem p){
        Query query = em.createQuery("delete FROM FilmItem p where p.key='"+p.getId()+"'");
        query.executeUpdate();
    }
    public List<FilmItem> findAll(){
        Query query = em.createQuery("FROM FilmItem");
        List <FilmItem> list = query.getResultList();
        return list;
    }
    public FilmItem findById(String id){
        FilmItem p = em.find(FilmItem.class, id);
        return p;
    }
}