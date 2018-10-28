package com.runtips.ricardo.runtipsmx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME= "runtipsmx.db";
    private static SQLiteDatabase readableDB = null;
    private static SQLiteDatabase writableDB = null;
    private static DBHelper instance = null;

    private DBHelper(Context context, int a) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static synchronized SQLiteDatabase getWritableDatabase(Context context){
        if(writableDB == null){
            writableDB = getHelper(context).getReadableDatabase();
        }
        return writableDB;
    }

    public static synchronized SQLiteDatabase getReadableDatabase(Context context){
        if(readableDB == null){
            readableDB = getHelper(context).getReadableDatabase();
        }
        return readableDB;
    }

    private static synchronized DBHelper getHelper(Context context){
        if(instance ==  null){
            instance = new DBHelper(context.getApplicationContext(), 1);
        }
        return instance;
    }
}
