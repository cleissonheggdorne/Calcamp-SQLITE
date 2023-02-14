package com.example.calcamp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CALCAMP.db";
    public static final String LOCAL_DB = "/data/data/com.example.calcamp/databases/";
    private Context context;
    private SQLiteDatabase sqliteDb;

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //initializebd();
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Migrations migration = new Migrations();
        db.execSQL(migration.getTableTeam());
        db.execSQL(migration.getTableLeague());
        db.execSQL(migration.getTablePunctuationType());
        db.execSQL(migration.getTableTeamLeague());
        db.execSQL(migration.getTablePunctuationPosition());
        db.execSQL(migration.getTriggerTeamLeagueClassification());
        db.execSQL(migration.getInsertPunctuationType());
        db.execSQL(migration.getInsertPunctuationPosition());
        db.execSQL(migration.getViewClassification());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDataBase(){
        String dbPath = context.getDatabasePath(DATABASE_NAME).getPath();
        if(sqliteDb != null && sqliteDb.isOpen()){
            return;
        }else{
            sqliteDb = SQLiteDatabase.openDatabase(dbPath, null,SQLiteDatabase.OPEN_READWRITE);
        }
    }

    public void closeDataBase(){
        if(sqliteDb != null){
            sqliteDb.close();
        }
    }

    private void initializebd() {
        File dbFile = context.getApplicationContext().getDatabasePath(DATABASE_NAME);
        if (dbFile.exists() == false){
            getReadableDatabase();
            if(copyData(context)){
                alert("Banco copiado com sucesso");
            }else{
                alert("Não foi possível copiar o banco");
            }
        }
    }

    private void alert(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    private boolean copyData(Context context) {
        try {
            InputStream is = context.getAssets().open(DATABASE_NAME);
            String outFile = LOCAL_DB + DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFile);
            byte[] buff = new byte[1024];
            int lenght = 0;
            while ((lenght = is.read(buff)) > 0) {
                outputStream.write(buff, 0, lenght);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
