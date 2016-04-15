package com.example.liaozhenbin.wenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liaozhenbin.wenote.R;

/**
 * Created by liaozhenbin on 2016/1/2.
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener{
    private Button bt_sign;
    private TextView tv_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initActionBar();

        bt_sign = (Button) findViewById(R.id.bt_sign);
        tv_login = (TextView) findViewById(R.id.tv_login);
        bt_sign.setOnClickListener(this);
        tv_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_sign:
                startActivity(new Intent(this,SignUp2Activity.class));
                finish();
                break;
            case R.id.tv_login:
                finish();
                break;
        }
    }
}