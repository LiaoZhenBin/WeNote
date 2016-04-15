package com.example.liaozhenbin.wenote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liaozhenbin on 2016/1/5.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "2bNote.db";
    private static final  int DATABASE_VERSION = 1;

    private static final String CREATE_NOTE = "create table we_note(" +
            "_id integer primary key autoincrement," +
            "author text," +
            "title text," +
            "content text," +
            "data bigint," +
            "path text)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}