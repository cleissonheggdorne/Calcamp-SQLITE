package com.example.calcamp.model.implement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.db.Migrations;
import com.example.calcamp.model.dao.TeamLeagueDAO;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.PunctuationType;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.model.entities.TeamLeague;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamLeagueDaoSQLITE implements TeamLeagueDAO {
    private DataBaseHelper dbh;
    private SQLiteDatabase sqliteDb;


    public TeamLeagueDaoSQLITE(DataBaseHelper dbh) {
        this.dbh = dbh;
        this.sqliteDb = dbh.getWritableDatabase();
    }

    @Override
    public long insert(TeamLeague obj) {
        dbh.openDataBase();
        if(findByIdLeague(obj) != null){
            return -1;
        };

        ContentValues contentValues = new ContentValues();
        contentValues.put("id_team", obj.getTeam().getId());
        contentValues.put("id_league", obj.getLeague().getId());
        contentValues.put("position", obj.getPosition());
        contentValues.put("match", obj.getMatch());
        long id = sqliteDb.insert("team_league", null, contentValues);

        dbh.close();
        sqliteDb.close();
        return 0;
    }

    @Override
    public long update(TeamLeague obj) {
        dbh.openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("position", obj.getPosition());
        String whereClause = "id_team= ? and id_league= ? and match=?";
        String [] whereArgs = new String[]{Integer.toString(obj.getTeam().getId()),
                Integer.toString(obj.getLeague().getId()), Integer.toString(obj.getMatch())};
        long state = sqliteDb.update("team_league",contentValues, whereClause, whereArgs);

        return state;
    }

    @Override
    public long deleteById(Integer id) {
        return 0;
    }

    @Override
    public List<TeamLeague> findByIdLeague(Integer id, Integer... match) {
        dbh.openDataBase();
        String where = "";
        if (match.length > 0){
            where = "WHERE tl.id_league = " + id +" AND \n" +
                "tl.match = " + match[0];
        }else{
            where = "WHERE tl.id_league = " + id;
        }
        List<TeamLeague> list = new ArrayList<TeamLeague>();
        String sql = "select t.id as idTeam, t.name as nameTeam," +
                "l.id as idLeague, l.name as nameLeague," +
                " l.id_punctuation_type as idPunctuationType, pt.name as namePuncutuationType," +
                " tl.position, tl.punctuation from team_league tl\n" +
                "inner join team t on\n" +
                "t.id = tl.id_team\n" +
                "inner join league l on\n" +
                "l.id = tl.id_league\n" +
                "inner join punctuation_type pt on\n" +
                "pt.id = l.id_punctuation_type\n" +
                 where;
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    Team team = instantiateTeam(cursor);
                    PunctuationType punctuationType = instantiatePunctuationType(cursor);
                    League league = instantiateLeague(cursor, punctuationType);
                    TeamLeague teamLeague = instantiateTeamLeague(cursor, team, league);
                    list.add(teamLeague);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        dbh.close();
        sqliteDb.close();
        return list;
    }

    protected Team instantiateTeam(Cursor cursor){
        Team team = new Team();
        team.setId(cursor.getInt(0));
        team.setName(cursor.getString(1));
        return team;
    }

    protected League instantiateLeague(Cursor cursor, PunctuationType pt) {
        League league = new League();
        league.setId(cursor.getInt(2));
        league.setName(cursor.getString(3));
        league.setPunctuationType(pt);
        return league;
    }

    protected PunctuationType instantiatePunctuationType(Cursor cursor) {
        PunctuationType punctuationType = new PunctuationType();
        punctuationType.setId(cursor.getInt(4));
        punctuationType.setName(cursor.getString(5));
        return punctuationType;
    }

    private TeamLeague instantiateTeamLeague(Cursor cursor, Team team, League league) {
        TeamLeague teamLeague = new TeamLeague();
        teamLeague.setTeam(team);
        teamLeague.setLeague(league);
        teamLeague.setPosition(cursor.getInt(6));
        teamLeague.setPunctuation(7);
        return teamLeague;
    }

    @Override
    public List<TeamLeague> findAll() {
        return null;

    }

    protected TeamLeague findByIdLeague(TeamLeague tl) {
        TeamLeague teamLeague = null;
        String sql = "select t.id as idTeam, t.name as nameTeam," +
                "l.id as idLeague, l.name as nameLeague," +
                " l.id_punctuation_type as idPunctuationType, pt.name as namePuncutuationType," +
                " tl.position, tl.punctuation from team_league tl\n" +
                "inner join team t on\n" +
                "t.id = tl.id_team\n" +
                "inner join league l on\n" +
                "l.id = tl.id_league\n" +
                "inner join punctuation_type pt on\n" +
                "pt.id = l.id_punctuation_type\n" +
                "WHERE tl.id_league = " + tl.getLeague().getId() + "\n"+
                "AND tl.id_team = "+ tl.getTeam().getId();
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    Team team = instantiateTeam(cursor);
                    PunctuationType punctuationType = instantiatePunctuationType(cursor);
                    League league = instantiateLeague(cursor, punctuationType);
                    teamLeague = instantiateTeamLeague(cursor, team, league);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return teamLeague;
    }

    public Integer amountMatch(Integer id){
        dbh.openDataBase();
        Integer amount = 0;
        String sql = "SELECT MAX(match) FROM team_league\n" +
                "WHERE id_league = " + id;
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if(cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                amount = cursor.getInt(0);
            }
        }
        dbh.close();
        sqliteDb.close();
        return amount;
    }
}
