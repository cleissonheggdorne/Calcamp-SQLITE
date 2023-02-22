package com.example.calcamp.model.dao;

import com.example.calcamp.model.entities.TeamLeague;
import com.example.calcamp.model.entities.view.Classification;

import java.util.List;

public interface ClassificationDAO {
        List<Classification> findByIdLeague(Integer id);
}


