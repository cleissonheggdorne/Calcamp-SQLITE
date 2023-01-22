package com.example.calcamp.model.dao;

import com.example.calcamp.model.entities.PunctuationType;

import java.util.List;

public interface PunctuationTypeDAO {
    long insert(PunctuationType obj);
    long update(PunctuationType obj);
    long deleteById(Integer id);
    PunctuationType findById(Integer id);
    List<PunctuationType> findAll();
    PunctuationType findByName(String name);
}
