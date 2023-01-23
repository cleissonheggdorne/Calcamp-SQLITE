package com.example.calcamp.controller;

import android.content.Context;

import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.dao.PunctuationPositionDAO;
import com.example.calcamp.model.dao.PunctuationTypeDAO;
import com.example.calcamp.model.entities.PunctuationPosition;
import com.example.calcamp.model.entities.PunctuationType;
import com.example.calcamp.model.implement.PunctuationPositionDaoSQLITE;

import java.util.ArrayList;
import java.util.List;

public class PunctuationPositionController {
    PunctuationPositionDAO punctuationPositionDAO;
    Context context;
    public PunctuationPositionController(Context context){
        this.context = context;
    }

    public List<PunctuationPosition> findScore(Integer idPunctuationType, Integer position){
        punctuationPositionDAO = DAOFactory.createPunctuationPositionDao(context);
        return punctuationPositionDAO.findScore(idPunctuationType, position);
    }

}
