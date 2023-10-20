package com.crud.test.repository;

import java.util.*;
import com.crud.test.model.Konsumen;

public interface KonsumenRepository {
    int save(Konsumen konsumen);

    int update(Konsumen konsumen);

    Konsumen findById(int id);

    int deleteById(int id);

    List<Konsumen> findAll();

    List<Konsumen> findByStatus(String status);

    List<Konsumen> findByFilter(String filter);

    int deleteAll();    
}
