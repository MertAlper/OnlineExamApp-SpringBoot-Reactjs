package com.example.examapp.demo.dao;

import java.util.List;

public interface Dao<K> {

    public K getEntityById(long id);
    public List<K> getAllEntities();
    public K save(K obj);
    public void delete(K obj);

}
