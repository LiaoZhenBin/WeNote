package com.example.liaozhenbin.wenote.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaozhenbin.wenote.R;

/**
 * Created by liaozhenbin on 2016/1/4.
 */
public class MoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more,container,false);
        return view;
    }

}
