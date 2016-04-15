package com.example.liaozhenbin.wenote.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liaozhenbin.wenote.R;
import com.example.liaozhenbin.wenote.adapter.PhotoAdapter;
import com.example.liaozhenbin.wenote.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends BaseActivity {
    private TextView tv_title;
    private TextView tv_data;
    private TextView tv_content;

    private List<String> photoList;
    private PhotoAdapter photoAdapter;
    private String imageFilePath;

    private DBManager manager;
    public Cursor cursor;

    private GridView gridView;
    private ProgressBar progressBar;

    private static final int BUFFER_TAG =1;

    /**
     * 使用异步解决页面卡顿现象
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == BUFFER_TAG){
                progressBar.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initActionBar();

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_data = (TextView) findViewById(R.id.tv_date);
        tv_content = (TextView) findViewById(R.id.tv_content);
        gridView = (GridView) findViewById(R.id.photosGrid);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String item = getIntent().getStringExtra("id");

        manager = new DBManager(this);
        cursor = manager.queryById(item);
        cursor.moveToFirst();

        String title = cursor.getString(cursor.getColumnIndex("title"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        String data = cursor.getString(cursor.getColumnIndex("data"));
        String path = cursor.getString(cursor.getColumnIndex("path"));

        tv_title.setText(title);
        tv_data.setText(data);
        tv_content.setText(content);

        photoList = new ArrayList<>();
        String[] array = path.split("\\$\\$\\$");

        for (String s:array){
            photoList.add(s);
        }

        photoAdapter = new PhotoAdapter(this, R.layout.item_photo, photoList);
        gridView.setAdapter(photoAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showIntent = new Intent(ContentActivity.this, ShowActivity.class);
                imageFilePath = photoList.get(position);
                showIntent.putExtra("path", imageFilePath);
                startActivity(showIntent);
            }

        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendEmptyMessage(BUFFER_TAG);
                }
            }
        }).start();
    }
}