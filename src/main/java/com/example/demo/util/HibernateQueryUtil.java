package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class HibernateQueryUtil {
    
    public static String buildHQLQuery(String entityName, String condition) {
        return "FROM " + entityName + " WHERE " + condition;
    }
    
    public static String buildCountQuery(String entityName) {
        return "SELECT COUNT(*) FROM " + entityName;
    }
}