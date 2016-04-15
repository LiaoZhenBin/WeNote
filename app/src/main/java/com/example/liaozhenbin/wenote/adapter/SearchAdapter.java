package com.example.liaozhenbin.wenote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.liaozhenbin.wenote.R;
import com.example.liaozhenbin.wenote.domain.MyNote;

import java.util.List;

/**
 * Created by liaozhenbin on 2016/3/21.
 */
public class SearchAdapter extends ArrayAdapter<MyNote> {
    private int resourceId;

    public SearchAdapter(Context context, int resource, List<MyNote> list) {
        super(context, resource, list);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyNote note = getItem(position);
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.id = (TextView) view.findViewById(R.id.tv_id);
            viewHolder.title = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.data = (TextView) view.findViewById(R.id.tv_date);
            viewHolder.content = (TextView) view.findViewById(R.id.tv_content);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.id.setText(note.getId()+"");
        viewHolder.title.setText(note.getTitle());
        viewHolder.data.setText(note.getData());
        viewHolder.content.setText(note.getContent());
        return view;
    }

    public class ViewHolder {
        TextView id;
        TextView title;
        TextView data;
        TextView content;
    }
}
