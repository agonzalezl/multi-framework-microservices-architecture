package com.example.inventory.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.inventory.model.Movie;

@Stateless
public class  ServiceBean   {
    @PersistenceContext
    private EntityManager em;
    public void put(Movie p){
        em.persist(p);
    }
    public void delete(Movie p){
        Query query = em.createQuery("delete FROM Movie p where p.key='"+p.getKey()+"'");
        query.executeUpdate();
    }
    public List<Movie> findAll(){
        Query query = em.createQuery("FROM Movie");
        List <Movie> list = query.getResultList();
        return list;
    }
    public Movie findById(String id){
        Movie p = em.find(Movie.class, id);
        return p;
    }
}