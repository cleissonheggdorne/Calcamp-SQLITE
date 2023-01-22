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
    public static final String DATABASE_NAME = "base.db";
    public static final String LOCAL_DB = "/data/data/com.example.calcamp/databases/";
    private Context context;
    private SQLiteDatabase sqliteDb;

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        initializebd();
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
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

//    public List<Team> findAll(){
//        openDataBase();
//        sqliteDb = this.getWritableDatabase();
//        List<Team> list = new ArrayList<Team>();
//        String sql = "SELECT * FROM team ORDER by name";
//        Cursor cursor = sqliteDb.rawQuery(sql, null);
//        if (cursor.getCount() > 0){
//            if(cursor.moveToFirst()){
//                do{
//                    Team team = new Team(cursor.getInt(0), cursor.getString(1));
//                    list.add(team);
//                }while(cursor.moveToNext());
//            }
//        }
//        cursor.close();
//        sqliteDb.close();
//        return list;
//    }

    private void initializebd() {
        //database = new DataBaseHelper(this);
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
