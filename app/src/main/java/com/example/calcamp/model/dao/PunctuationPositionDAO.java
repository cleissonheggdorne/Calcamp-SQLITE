package com.example.calcamp.model.dao;

import com.example.calcamp.model.entities.PunctuationPosition;
import com.example.calcamp.model.entities.PunctuationType;

import java.util.List;

public interface PunctuationPositionDAO {
    List<PunctuationPosition> findScore(Integer idPunctuationType, Integer position);
}
