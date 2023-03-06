package com.example.calcamp.model.implement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.db.Migrations;
import com.example.calcamp.model.dao.TeamDAO;
import com.example.calcamp.model.entities.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDaoJDBC implements TeamDAO {
    private DataBaseHelper dbh;
    private SQLiteDatabase sqliteDb;


    public TeamDaoJDBC (DataBaseHelper dbh) {
        this.dbh = dbh;
        this.sqliteDb = dbh.getWritableDatabase();
    }

    @Override
    public long insert(Team obj) {
        dbh.openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        contentValues.put("image", obj.getImage());
        long id = sqliteDb.insert("team", null, contentValues);
        dbh.close();
        sqliteDb.close();
        return id;
    }

    @Override
    public long update(Team obj) {
        dbh.openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        //contentValues.put("image", obj.getImage());
        long ret = sqliteDb.update("team", contentValues, "id = "+ obj.getId(), null);
        dbh.close();
        sqliteDb.close();
        return ret;
    }

    @Override
    public long deleteById(Integer id) {
        dbh.openDataBase();
        long ret = sqliteDb.delete("team", "id = " + id, null);
        dbh.close();
        sqliteDb.close();
        return ret;
    }

    @Override
    public Team findById(Integer id) {
        dbh.openDataBase();
        String sql = "SELECT * FROM team WHERE" + id;
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    return(new Team(cursor.getInt(cursor.getColumnIndexOrThrow("id")), cursor.getString(cursor.getColumnIndexOrThrow("name")), cursor.getBlob(cursor.getColumnIndexOrThrow("image"))));
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        sqliteDb.close();
        return null;
    }

    private Team instantiateTeam(ResultSet rs) throws SQLException {
        Team team = new Team();
        team.setId(rs.getInt("id"));
        team.setName(rs.getString("name"));
        return team;
    }

    @Override
    public List<Team> findAll(){
        dbh.openDataBase();
        List<Team> list = new ArrayList<Team>();
        String sql = "SELECT * FROM team ORDER by name";
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    Team team = new Team(cursor.getInt(cursor.getColumnIndexOrThrow("id")), cursor.getString(cursor.getColumnIndexOrThrow("name")), cursor.getBlob(cursor.getColumnIndexOrThrow("image")));
                    list.add(team);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        sqliteDb.close();
        return list;
    }

    @Override
    public List<Team> findNoLeague(Integer id){
        dbh.openDataBase();
        List<Team> list = new ArrayList<Team>();
        String sql = "select * from team\n"
                    +"where id not in (select id_team from team_league\n"
                    +"where id_league = "+ id +")";
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    Team team = new Team(cursor.getInt(cursor.getColumnIndexOrThrow("id")), cursor.getString(cursor.getColumnIndexOrThrow("name")), cursor.getBlob(cursor.getColumnIndexOrThrow("image")));
                    list.add(team);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        sqliteDb.close();
        return list;
    }

}
