package com.example.liaozhenbin.wenote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaozhenbin.wenote.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by liaozhenbin on 2015/12/31.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_login;
    private TextView tv_sign;
    private EditText et_username;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initActionBar();

        bt_login = (Button) findViewById(R.id.bt_login);
        tv_sign = (TextView) findViewById(R.id.tv_sign);
        bt_login.setOnClickListener(this);
        tv_sign.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        et_username.addTextChangedListener(new MyWatch());
        et_password.addTextChangedListener(new MyWatch());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                String userName = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if(login(userName, password)){
                    startActivity(new Intent(this, MainActivity.class));
                    SharedPreferences preferences = getSharedPreferences("config",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("author",userName);
                    editor.commit();
                    finish();
                }else{
                    Toast.makeText(this,"用户名密码错误！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_sign:
//                Toast.makeText(this, "Sign TextView onClick", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }

    /**
     * 校验用户名密码是否正确
     * @param userName
     * @param password
     * @return
     */
    private boolean login(String userName, String password) {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = openFileInput("info");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while((line = reader.readLine())!= null){
                String [] info = line.split("\\$\\$");
                if(userName.equals(info[0])&&password.equals(info[1])){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    class MyWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!"".equals(et_password.getText().toString().trim())
                    && !"".equals(et_username.getText().toString().trim())) {
                bt_login.setEnabled(true);
                bt_login.setTextColor(Color.WHITE);
            } else {
                bt_login.setEnabled(false);
                bt_login.setTextColor(Color.GRAY);
            }
        }
    }
}