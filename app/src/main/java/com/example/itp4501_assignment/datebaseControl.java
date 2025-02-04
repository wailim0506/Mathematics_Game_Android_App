package com.example.itp4501_assignment;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class datebaseControl {
    private static SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501_assignment/gameDB",null,SQLiteDatabase.CREATE_IF_NECESSARY);

    public datebaseControl(){
        if (!db.isOpen()){
            openDB();
        }
    }

    private void openDB(){
        db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501_assignment/gameDB",null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDB(){
        db.close();
    }
    public Cursor ExecuteQuery(String sql,String[] arg){
        try{
            return db.rawQuery(sql,arg);
        }catch(SQLiteException e){
            e.printStackTrace();
            throw e;
        }
    }

    public boolean ExecuteNonQuery(String sql){
        try{
            db.execSQL(sql);
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }
}
