package com.example.liaozhenbin.wenote.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liaozhenbin.wenote.R;
import com.example.liaozhenbin.wenote.adapter.SearchAdapter;
import com.example.liaozhenbin.wenote.db.DBManager;
import com.example.liaozhenbin.wenote.domain.MyNote;

import java.util.ArrayList;
import java.util.List;

import static com.example.liaozhenbin.wenote.R.id.list_view_search;

/**
 * Created by liaozhenbin on 2016/4/5.
 */
public class SearchActivity extends BaseActivity {
    private ViewGroup backIcon;
    private EditText et_search;
    private ListView searchList;
    private TextView tv_notFind;

    private DBManager manager;
    private List<MyNote> searchArray;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initActionBar();
        manager = new DBManager(this);

        backIcon = (ViewGroup) findViewById(R.id.back);
        et_search = (EditText) findViewById(R.id.et_search);
        searchList = (ListView) findViewById(list_view_search);
        tv_notFind = (TextView) findViewById(R.id.tv_notFind);

        searchArray = new ArrayList<>();
        adapter = new SearchAdapter(this,R.layout.item_note,searchArray);
        searchList.setAdapter(adapter);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchArray.clear();

                String searchStr = et_search.getText().toString().trim();

                if (TextUtils.isEmpty(searchStr)) {
                    adapter.notifyDataSetChanged();
                    return;
                }

                Cursor cursor = manager.search(searchStr);

                if (cursor != null && cursor.getCount() > 0) {
                    tv_notFind.setVisibility(View.INVISIBLE);
                    MyNote note = null;
                    while (cursor.moveToNext()) {
                        note = new MyNote();
                        note.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                        note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        note.setContent(cursor.getString(cursor.getColumnIndex("content")));
                        note.setData(cursor.getString(cursor.getColumnIndex("data")));
                        note.setPath(cursor.getString(cursor.getColumnIndex("path")));
                        note.setId(cursor.getInt(cursor.getColumnIndex("_id")));

                        searchArray.add(note);
                    }

                } else {
                    tv_notFind.setVisibility(View.VISIBLE);
                }

                adapter.notifyDataSetChanged();
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView) view.findViewById(R.id.tv_id)).getText().toString();
//                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this, ContentActivity.class);
                intent.putExtra("id", item);
                startActivity(intent);
            }
        });
    }

}