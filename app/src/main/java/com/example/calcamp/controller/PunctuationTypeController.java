package com.example.calcamp.controller;

import android.content.Context;

import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.dao.PunctuationTypeDAO;
import com.example.calcamp.model.entities.PunctuationType;

import java.util.ArrayList;
import java.util.List;

public class PunctuationTypeController {
    PunctuationTypeDAO punctuationTypeDao;
    Context context;
    public PunctuationTypeController(Context context){
        this.context = context;
    }

    public long saveController(PunctuationType punctuationType){
        punctuationTypeDao = DAOFactory.createPunctuationTypeDao(context);
        long id = punctuationTypeDao.insert(punctuationType);
        return id;
    }

    public PunctuationType findById(Integer id){
        punctuationTypeDao = DAOFactory.createPunctuationTypeDao(context);
        return punctuationTypeDao.findById(id);
    }

    public PunctuationType findByName(String name){
        punctuationTypeDao = DAOFactory.createPunctuationTypeDao(context);
        return punctuationTypeDao.findByName(name);
    }

    public long deleteController(Integer id){
        punctuationTypeDao = DAOFactory.createPunctuationTypeDao(context);
        long ret = punctuationTypeDao.deleteById(id);
        return ret;
    }

    public long updateController(PunctuationType punctuationType){
        punctuationTypeDao = DAOFactory.createPunctuationTypeDao(context);
        long ret = punctuationTypeDao.update(punctuationType);
        return ret;
    }

    public List<String> findAllController(){
        punctuationTypeDao = DAOFactory.createPunctuationTypeDao(context);
        List<PunctuationType> list = punctuationTypeDao.findAll();
        ArrayList<String> listString = new ArrayList<String>();
        for (PunctuationType  pt: list) {
            listString.add(pt.getName());
        }
        return(listString);
    }
}
