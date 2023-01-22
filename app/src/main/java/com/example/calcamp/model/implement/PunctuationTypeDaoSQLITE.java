package com.example.calcamp.model.implement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.model.dao.PunctuationTypeDAO;
import com.example.calcamp.model.entities.PunctuationType;

import java.util.ArrayList;
import java.util.List;

public class PunctuationTypeDaoSQLITE implements PunctuationTypeDAO {

    private DataBaseHelper dbh;
    private SQLiteDatabase sqliteDb;


    public PunctuationTypeDaoSQLITE(DataBaseHelper dbh) {
        this.dbh = dbh;
        this.sqliteDb = dbh.getWritableDatabase();
    }

    @Override
    public long insert(PunctuationType obj) {
        dbh.openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        long id = sqliteDb.insert("punctuation_type", null, contentValues);
        dbh.close();
        sqliteDb.close();
        return id;
    }

    @Override
    public long update(PunctuationType obj) {
        dbh.openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        //contentValues.put("image", obj.getImage());
        long ret = sqliteDb.update("punctuation_type", contentValues, "id = "+ obj.getId(), null);
        dbh.close();
        sqliteDb.close();
        return ret;
    }

    @Override
    public long deleteById(Integer id) {
        dbh.openDataBase();
        long ret = sqliteDb.delete("punctuation_type", "id = " + id, null);
        dbh.close();
        sqliteDb.close();
        return ret;
    }

    @Override
    public PunctuationType findById(Integer id) {
        dbh.openDataBase();
        String sql = "SELECT * FROM league WHERE id" + id;
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    return(new PunctuationType(cursor.getInt(0), cursor.getString(1)));
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        sqliteDb.close();
        return null;
    }

    @Override
    public PunctuationType findByName(String name) {
        dbh.openDataBase();
        String sql = "SELECT * FROM punctuation_type WHERE name=" + '"' + name + '"';
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    return(new PunctuationType(cursor.getInt(0), cursor.getString(1)));
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        sqliteDb.close();
        return null;
    }

    @Override
    public List<PunctuationType> findAll() {
        dbh.openDataBase();
        List<PunctuationType> list = new ArrayList<PunctuationType>();
        String sql = "SELECT * FROM punctuation_type ORDER by name";
        Cursor cursor = sqliteDb.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    PunctuationType punctuationType = new PunctuationType(cursor.getInt(0), cursor.getString(1));
                    list.add(punctuationType);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        sqliteDb.close();
        return list;
    }

}
