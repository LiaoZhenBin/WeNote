package com.example.liaozhenbin.wenote.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.liaozhenbin.wenote.R;
import com.example.liaozhenbin.wenote.fragments.IndexFragment;
import com.example.liaozhenbin.wenote.fragments.MoreFragment;
import com.example.liaozhenbin.wenote.fragments.WeatherFragment;
import com.example.liaozhenbin.wenote.fragments.WriteFragment;

/**
 * Created by liaozhenbin on 2015/12/31.
 */
public class MainActivity extends BaseActivity {
    private RadioGroup rg_group;

    public IndexFragment indexFragment;
    private WriteFragment writeFragment;
    private WeatherFragment weatherFragment;
    private MoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg_group = (RadioGroup) findViewById(R.id.rg_group);
        init();

        initActionBar();

        //给下面的页签栏设置点击侦听
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                hideFragments(transaction);
                switch (checkedId) {
                    case R.id.rb_index:
                        if (indexFragment != null) {
                            // 如果indexFragment不为空，则创建一个并添加到界面上
                            transaction.remove(indexFragment);
                        }
                        indexFragment = new IndexFragment();
                        transaction.add(R.id.fl_main, indexFragment);
                        break;
                    case R.id.rb_write:
                        if (writeFragment == null) {
                            // 如果writeFragment为空，则创建一个并添加到界面上
                            writeFragment = new WriteFragment();
                            transaction.add(R.id.fl_main, writeFragment);
                        } else {
                            // 如果writeFragment不为空，则直接将它显示出来
                            transaction.show(writeFragment);
                        }
                        break;
                    case R.id.rb_weather:
                        if (weatherFragment == null) {
                            // 如果weatherFragment为空，则创建一个并添加到界面上
                            weatherFragment = new WeatherFragment();
                            transaction.add(R.id.fl_main, weatherFragment);
                        } else {
                            // 如果weatherFragment不为空，则直接将它显示出来
                            transaction.show(weatherFragment);
                        }
                        break;
                    case R.id.rb_more:
                        if (moreFragment == null) {
                            // 如果moreFragment为空，则创建一个并添加到界面上
                            moreFragment = new MoreFragment();
                            transaction.add(R.id.fl_main, moreFragment);
                        } else {
                            // 如果moreFragment不为空，则直接将它显示出来
                            transaction.show(moreFragment);
                        }
                        break;
                }
                transaction.commit();
            }
        });
    }

    /**
     * 初始化
     */
    public void init() {
        //设置第一个RadioButton被选中
        ((RadioButton) findViewById(R.id.rb_index)).setChecked(true);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (indexFragment == null) {
            // 如果indexFragment为空，则创建一个并添加到界面上
            indexFragment = new IndexFragment();
            transaction.add(R.id.fl_main, indexFragment);
        } else {
            // 如果indexFragment不为空，则直接将它显示出来
            transaction.show(indexFragment);
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     * @param transaction 用于对Fragment执行操作的事务
     */
    public void hideFragments(FragmentTransaction transaction) {
        if (indexFragment != null) {
            transaction.hide(indexFragment);
        }
        if (writeFragment != null) {
            transaction.hide(writeFragment);
        }
        if (weatherFragment != null) {
            transaction.hide(weatherFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * menu点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                startActivity(new Intent(this,SearchActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * init ActionBar
     */
    public void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(false);

        actionBar.setDisplayShowTitleEnabled(false);
        int statusBarHeight = getResources().getDimensionPixelSize(
                getResources().getIdentifier("status_bar_height", "dimen", "android"));
        findViewById(R.id.bar_status).setMinimumHeight(statusBarHeight);
        findViewById(R.id.bar_action).setMinimumHeight(38+statusBarHeight);
    }
}