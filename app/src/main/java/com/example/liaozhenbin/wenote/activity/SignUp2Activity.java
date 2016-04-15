package com.example.liaozhenbin.wenote.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaozhenbin.wenote.R;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by liaozhenbin on 2016/1/2.
 */
public class SignUp2Activity extends BaseActivity implements View.OnClickListener{
    private EditText et_username;
    private EditText et_password;
    private EditText et_confirm_password;
    private Button bt_sign;
    private TextView tv_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign2);
        initActionBar();


        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        bt_sign = (Button) findViewById(R.id.bt_sign);
        tv_login = (TextView) findViewById(R.id.tv_login);

        bt_sign.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_sign:
                String userName = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String confirmPassword = et_confirm_password.getText().toString().trim();
                if (!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(password)&&
                        !TextUtils.isEmpty(confirmPassword)){
                    if (password.equals(confirmPassword)){
                        String savedInfo = userName +"$$" +password;
                        save(savedInfo);
                        Toast.makeText(this,"注册成功哦，亲，即将跳转到登入页面",Toast.LENGTH_SHORT).show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1200);
                                    finish();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }else{
                        Toast.makeText(this,"前后密码不一致哦，亲",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"不能为空哦，亲",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_login:
                finish();
                break;
        }
    }

    /**
     * 保存注册的账号密码
     * @param string
     */
    private void save(String string){
        BufferedWriter writer = null;
        try {
            FileOutputStream outputStream = openFileOutput("info", Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(string);
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}