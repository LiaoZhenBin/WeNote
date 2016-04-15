package com.example.liaozhenbin.wenote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.example.liaozhenbin.wenote.R;
import com.example.liaozhenbin.wenote.utils.PhotoShow;

import java.util.List;

/**
 * Created by liaozhenbin on 2016/3/10.
 */
public class PhotoAdapter extends ArrayAdapter<String> {
    private int resourceId;

    public PhotoAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String path = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;
        }
        ImageView photoImage = (ImageView) view.findViewById(R.id.item_photo);

        PhotoShow.show(photoImage, path);
        return view;
    }
}