package com.example.calcamp.model.implement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.db.Migrations;
import com.example.calcamp.model.dao.leagueDAO;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.PunctuationType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueDaoSQLITE implements leagueDAO {
    private DataBaseHelper dbh;
    private SQLiteDatabase sqliteDb;


    public LeagueDaoSQLITE(DataBaseHelper dbh) {
        this.dbh = dbh;
        this.sqliteDb = dbh.getWritableDatabase();
    }

    @Override
    public long insert(League obj) {
        dbh.openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        contentValues.put("id_punctuation_type", obj.getPunctuationType().getId());
        long id = sqliteDb.insert("league", null, contentValues);
        dbh.close();
        sqliteDb.close();
        return id;
    }

    @Override
    public long update(League obj) {
        dbh.openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        contentValues.put("id_punctuation_type", obj.getPunctuationType().getId());
        //contentValues.put("image", obj.getImage());
        long ret = sqliteDb.update("league", contentValues, "id = "+ obj.getId(), null);
        dbh.close();
        sqliteDb.close();
        return ret;
    }

    @Override
    public long deleteById(Integer id) {
        dbh.openDataBase();
        long ret = sqliteDb.delete("league", "id = " + id, null);
        dbh.close();
        sqliteDb.close();
        return ret;
    }

    @Override
    public League findById(Integer id) {
        dbh.openDataBase();
        String sql = "SELECT * FROM league WHERE" + id;
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    return(new League(cursor.getInt(0), cursor.getString(1), null));
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        sqliteDb.close();
        return null;
    }

    private League instantiateTeam(ResultSet rs) throws SQLException {
        League league = new League();
        league.setId(rs.getInt("id"));
        league.setName(rs.getString("name"));
        return league;
    }

    @Override
    public List<League> findAll(){
        dbh.openDataBase();
        String sql = "SELECT l.id, l.name,l.id_punctuation_type, pt.name as name_punctuation_type FROM league l\r\n"
                + "INNER JOIN punctuation_type pt on\r\n"
                + "pt.id = l.id_punctuation_type\r\n";

        List<League> list = new ArrayList<>();
     //   Map<Integer, PunctuationType> map = new HashMap<>();

        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    //PunctuationType pt = map.get(cursor.getInt(2));
                   // if(pt == null){
                        PunctuationType pt;
                        pt = instantiatePunctuationType(cursor);
                    //    map.put(cursor.getInt(3), pt);
                    //}
                    League league = instantiateLeague(cursor, pt);
                    list.add(league);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        sqliteDb.close();
        return list;
    }
    private League instantiateLeague(Cursor cursor, PunctuationType pt) {
        League league = new League();
        league.setId(cursor.getInt(0));
        league.setName(cursor.getString(1));
        league.setPunctuationType(pt);
        return league;
    }

    private PunctuationType instantiatePunctuationType(Cursor cursor) {
        PunctuationType punctuationType = new PunctuationType();
        punctuationType.setId(cursor.getInt(2));
        punctuationType.setName(cursor.getString(3));
        return punctuationType;
    }

}
