package com.example.demo.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Studententity;
@Service
public class Studentservice {
Map<Integer,Studententity> mp=new HashMap<>();
    public Studententity savedata(Studententity st){
        mp.put(st.getId(),st);
        return st;
    }
    public List<Studententity> retdata() {
         return new ArrayList<>(mp.values());
    }
    public Studententity id(int id) {
        
       // throw new UnsupportedOperationException("Unimplemented method 'id'");
       return mp.get(id);
    }
   
    public Studententity updateStudent(int id, Studententity st) {
     mp.replace(id,st);
     return mp.get(id);
    }
    public Studententity deleteStudent(int id) {
       return mp.remove(id);
    }
}