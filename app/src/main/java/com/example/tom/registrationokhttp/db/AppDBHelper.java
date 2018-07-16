package com.example.tom.registrationokhttp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tom.registrationokhttp.app.DBConstants;

public class AppDBHelper extends SQLiteOpenHelper {

    public AppDBHelper(Context context) {
        super(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBConstants.DB_TABLE_NAME +
                " (" + DBConstants.DB_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBConstants.DB_FIELD_HEADLINE + " TEXT NOT NULL, " +
                DBConstants.DB_FIELD_DESCRIPTION + " TEXT, " +
                DBConstants.DB_FIELD_LATITUDE + " TEXT, " +
                DBConstants.DB_FIELD_LONGITUDE + " TEXT, " +
                DBConstants.DB_FIELD_PATH + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.DB_TABLE_NAME);
//        onCreate(db);


    }


}
