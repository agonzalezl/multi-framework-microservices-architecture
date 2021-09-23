package com.example;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.Criteria;
import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public class UserDao {
    
	@PersistenceContext
    private EntityManager entityManager;
	
	public List getUserDetails() {
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(User.class);
        return criteria.list();
	}

}
