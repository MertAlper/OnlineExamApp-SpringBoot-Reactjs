package com.example.examapp.demo.service;

import java.util.List;

public interface GenericService<K> {

    public K getById(long id);
    public List<K> getAll();
    public K save(K obj);
    public void delete(K obj);

}
