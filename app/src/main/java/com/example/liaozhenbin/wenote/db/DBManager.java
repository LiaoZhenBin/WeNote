package com.example.liaozhenbin.wenote.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.liaozhenbin.wenote.domain.MyNote;

/**
 * Created by liaozhenbin on 2016/1/6.
 */
public class DBManager {
    private SQLiteDatabase database;
    private DBHelper helper;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }

    /**
     * 添加数据
     * @param note
     */
    public void add(MyNote note) {
        ContentValues values = new ContentValues();
        values.put("author", note.getAuthor());
        values.put("title",note.getTitle());
        values.put("content", note.getContent());
        values.put("data", note.getData());
        values.put("path",note.getPath());
        database.insert("we_note", null, values);
    }

    /**
     * 查询所有Cursor
     * @param author
     * @return
     */
    public Cursor queryTheCursor(String author) {
        Cursor cursor = database.query("we_note", null,"author = ?", new String[]{author}, null, null,
                "_id desc", null);
        return cursor;
    }

    /**
     * 查询选择的Cursor
     * @param id
     * @return
     */
    public Cursor queryById(String id){
        Cursor cursor = database.query("we_note", null,"_id = ?", new String[]{id}, null, null,
                "_id desc", null);
        return cursor;
    }

    public Cursor search(String searchStr){
        Cursor cursor = database.rawQuery("select * from we_note where content like '%"+ searchStr + "%'", null);
        return cursor;
    }

    /**
     *
     * @param id
     */
    public void deleteById(String id){
        database.delete("we_note","_id = ?",new String[]{id});
    }

    public void close() {
        database.close();
    }
}