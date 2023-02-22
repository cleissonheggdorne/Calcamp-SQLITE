package com.example.calcamp.controller;

import android.content.Context;

import com.example.calcamp.model.dao.ClassificationDAO;
import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.dao.leagueDAO;
import com.example.calcamp.model.entities.view.Classification;

import java.util.List;

public class ClassificationController {
    ClassificationDAO classificationDAO;
    Context context;
    public ClassificationController(Context context){
        this.context = context;
    }

    public List<Classification> findByIdLeague(Integer id){
        classificationDAO = DAOFactory.createClassificationDao(context);
        return classificationDAO.findByIdLeague(id);
    }

}
