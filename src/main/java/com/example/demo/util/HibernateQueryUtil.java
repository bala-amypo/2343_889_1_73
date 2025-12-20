package com.example.demo.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class HibernateQueryUtil {
    public static Query createQuery(EntityManager em, String hql) {
        return em.createQuery(hql);
    }
}
