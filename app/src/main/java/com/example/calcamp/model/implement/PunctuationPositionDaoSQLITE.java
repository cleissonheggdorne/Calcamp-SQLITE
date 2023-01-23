package com.example.calcamp.model.implement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.model.dao.PunctuationPositionDAO;
import com.example.calcamp.model.dao.PunctuationTypeDAO;
import com.example.calcamp.model.entities.PunctuationPosition;
import com.example.calcamp.model.entities.PunctuationType;
import com.example.calcamp.model.entities.Team;

import java.util.ArrayList;
import java.util.List;

public class PunctuationPositionDaoSQLITE implements PunctuationPositionDAO {

    private DataBaseHelper dbh;
    private SQLiteDatabase sqliteDb;


    public PunctuationPositionDaoSQLITE(DataBaseHelper dbh) {
        this.dbh = dbh;
        this.sqliteDb = dbh.getWritableDatabase();
    }

    @Override
    public List<PunctuationPosition> findScore(Integer idPunctuationType, Integer position) {
        dbh.openDataBase();

        String argWhere1 = "select * from punctuation_position pp\n"+
                            "inner join punctuation_type pt on\n"+
                            "pp.id_punctuation_type = pt.id\n"+
                            "where pp.id_punctuation_type =" +idPunctuationType+"\n";
        String argWhere2 = "and pp.position = " + position + "\n";
        String sql = argWhere1;
        if(position != null){
            sql += argWhere2;
        }
        Cursor cursor = sqliteDb.rawQuery(sql, null);

        PunctuationPosition punctuationPosition = new PunctuationPosition();
        List<PunctuationPosition> list = new ArrayList<PunctuationPosition>();
        while (cursor.moveToNext()){
            PunctuationType punctuationType =  instantiatePunctuationType(cursor);
            punctuationPosition.setPunctuationType(punctuationType);
            punctuationPosition.setPosition(cursor.getInt(1));
            punctuationPosition.setScore(cursor.getInt(2));
            list.add(punctuationPosition);
        }
        cursor.close();
        dbh.close();
        sqliteDb.close();
        return list;
    }

    protected PunctuationType instantiatePunctuationType(Cursor cursor) {
        PunctuationType punctuationType = new PunctuationType();
        punctuationType.setId(cursor.getInt(3));
        punctuationType.setName(cursor.getString(4));
        return punctuationType;
    }
}
