package com.example.liaozhenbin.wenote.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaozhenbin.wenote.activity.ContentActivity;
import com.example.liaozhenbin.wenote.R;
import com.example.liaozhenbin.wenote.db.DBManager;

/**
 * Created by liaozhenbin on 2016/1/4.
 */
public class IndexFragment extends Fragment {
    private ListView contentListView;
    private DBManager manager;
    public Cursor cursor;
    public SimpleCursorAdapter adapter;

    private String longItem;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contentListView = (ListView) getActivity().findViewById(R.id.list_view_index);

        manager = new DBManager(getActivity());

        SharedPreferences preferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        String author = preferences.getString("author", "");

        cursor = manager.queryTheCursor(author);

        getActivity().startManagingCursor(cursor);

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.item_note, cursor,
                new String[]{"_id", "title", "data", "content"}, new int[]{R.id.tv_id, R.id.tv_title,
                R.id.tv_date, R.id.tv_content});
        contentListView.setAdapter(adapter);

        //为 ListView 的所有 item 注册 ContextMenu
        registerForContextMenu(contentListView);

        contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView) view.findViewById(R.id.tv_id)).getText().toString();
//                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("id", item);
                startActivity(intent);
            }
        });

        //获取长按Item的_id;
        contentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longItem = ((TextView) view.findViewById(R.id.tv_id)).getText().toString();
//                Toast.makeText(getActivity(), longItem, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    //上下文菜单，本例会通过长按条目激活上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu  menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        //添加菜单项
        menu.add(0, 0, 0, "置顶");
        menu.add(0, 1, 0, "删除");
    }

    //菜单单击响应
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //获取当前被选择的菜单项的信息
        //AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        //Log.i("braincol",String.valueOf(info.id));
        switch (item.getItemId()) {
            case 0:
                //在这里添加处理代码
                Toast.makeText(getActivity(),"置顶",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                //在这里添加处理代码
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("删除日记");
                builder.setMessage("你确定要删除这条日记？");

                builder.setPositiveButton("我意已决", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除笔记
                        manager.deleteById(longItem);

                        //重新查找cursor,调用notifyDataSetChanged通知更新。
                        cursor.requery();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("容我考虑",null);

                builder.show();
                break;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        return view;
    }

}