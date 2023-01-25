package com.example.calcamp.db;

import android.database.sqlite.SQLiteDatabase;

public class Migrations {

    private DataBaseHelper dbh;
    private SQLiteDatabase sqliteDb;


    public Migrations(DataBaseHelper dbh) {
        this.dbh = dbh;
        this.sqliteDb = dbh.getWritableDatabase();
    }

    protected void createTrigger(){
        String sql = "CREATE TRIGGER IF NOT EXISTS trigger_team_ligue_classification\n" +
                "         AFTER INSERT\n" +
                "            ON team_ligue\n" +
                "BEGIN\n" +
                "    UPDATE team_ligue\n" +
                "       SET punctuation = pp.score\n" +
                "      FROM team_ligue tl\n" +
                "           INNER JOIN\n" +
                "           league l ON tl.id_league = l.id\n" +
                "           INNER JOIN\n" +
                "           punctuation_position pp ON pp.id_punctuation_type = l.id_punctuation_type AND \n" +
                "                                      pp.position = tl.position\n" +
                "     WHERE team_ligue.id_league = tl.id_league AND \n" +
                "           team_ligue.id_team = tl.id_team AND \n" +
                "           team_ligue.position = tl.position AND \n" +
                "           tl.id_league = NEW.id_league AND \n" +
                "           tl.id_team = NEW.id_team AND \n" +
                "           tl.position = NEW.position;\n" +
                "END;\n";
        dbh.openDataBase();
        sqliteDb.execSQL(sql);
    }

    protected void AddColum(String table,String nameColumn, String type, Boolean chave, Boolean valueNull){
        String sql = "ALTER TABLE " + table + " ADD " + nameColumn + " " + type;
        if(chave){
            sql += " Primary Key";
        }
        if(!valueNull){
            sql += "NOT NULL";
        }
        dbh.openDataBase();
        sqliteDb.execSQL(sql);
    }
}
