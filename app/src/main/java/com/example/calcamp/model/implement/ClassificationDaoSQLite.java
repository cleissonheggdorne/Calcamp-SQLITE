package com.example.calcamp.model.implement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.model.dao.ClassificationDAO;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.PunctuationType;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.model.entities.view.Classification;

import java.util.ArrayList;
import java.util.List;

public class ClassificationDaoSQLite implements ClassificationDAO {
    private DataBaseHelper dbh;
    private SQLiteDatabase sqliteDb;


    public ClassificationDaoSQLite(DataBaseHelper dbh) {
        this.dbh = dbh;
        this.sqliteDb = dbh.getWritableDatabase();
    }


    @Override
    public List<Classification> findByIdLeague(Integer id) {
        dbh.openDataBase();
        List<Classification> list = new ArrayList<>();
        String sql = "select vc.id_team as id_team, \n" +
                "       t.name as name_team,\n" +
                "       t.image as image_team,\n" +
                "       vc.id_league as id_league,\n" +
                "       l.name as name_league,\n" +
                "       l.id_punctuation_type as id_punctuation_type_league,\n" +
                "       pt.name as name_punctuation_type,\n" +
                "       l.amount_match as amount_match_league,\n" +
                "       vc.punctuation_final,\n" +
                "       vc.position_final\n      " +
                "        from view_classification vc\n" +
                "inner join team t on\n" +
                "vc.id_team = t.id \n" +
                "inner join league l on\n" +
                "vc.id_league = l.id\n" +
                "inner join punctuation_type pt on\n" +
                "l.id_punctuation_type = pt.id\n" +
                "WHERE vc.id_league =" + id;
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    Team team = instantiateTeam(cursor);
                    PunctuationType punctuationType = instantiatePunctuationType(cursor);
                    League league = instantiateLeague(cursor, punctuationType);
                    Classification classification = instantiateClassification(team, league, cursor);
                    list.add(classification);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        dbh.close();
        sqliteDb.close();
        return list;
    }

    private Classification instantiateClassification(Team team, League league, Cursor cursor) {
        return new Classification(team, league, cursor.getInt(8), cursor.getInt(9));
    }

    protected Team instantiateTeam(Cursor cursor){
        Team team = new Team();
        team.setId(cursor.getInt(0));
        team.setName(cursor.getString(1));
        return team;
    }

    protected PunctuationType instantiatePunctuationType(Cursor cursor) {
        PunctuationType punctuationType = new PunctuationType();
        punctuationType.setId(cursor.getInt(5));
        punctuationType.setName(cursor.getString(6));
        return punctuationType;
    }

    protected League instantiateLeague(Cursor cursor, PunctuationType pt) {
        League league = new League();
        league.setId(cursor.getInt(3));
        league.setName(cursor.getString(4));
        league.setPunctuationType(pt);
        return league;
    }

}
