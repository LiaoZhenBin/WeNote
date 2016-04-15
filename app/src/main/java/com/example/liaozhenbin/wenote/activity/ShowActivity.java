package com.example.liaozhenbin.wenote.activity;

import android.content.Intent;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.liaozhenbin.wenote.R;

public class ShowActivity extends BaseActivity {

    private ImageView photo;
    private String imageFilePath;
    private ProgressBar progressBar;

    /**
     * 使用异步解决页面卡顿现象
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
            photo.setVisibility(View.VISIBLE);
            Bitmap bitmap = (Bitmap) msg.obj;
            photo.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initActionBar();

        Intent intent = getIntent();
        imageFilePath = intent.getStringExtra("path");

        photo = (ImageView) findViewById(R.id.iv_photo);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        show(imageFilePath);
    }

    private void show(final String path){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //取得屏幕的显示大小
                    Display currentDisplay = getWindowManager().getDefaultDisplay();
                    int dw = currentDisplay.getWidth();
                    int dh = currentDisplay.getHeight();

                    //对拍出的照片进行缩放
                    BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();

            /*
            * 官方文档这样写着“If set to true, the decoder will return null (no bitmap),
            * but the out... fields will still be set, allowing the caller to query
            * the bitmap without having to allocate the memory for its
            * pixels.”，大意就是说inJustDecodeBounds 为true时，不给出实际的Bitmap对象，
            * 但你可以得到这张图片的实际信息，这样你就可以计算缩放比例了
            */
                    //请求图片属性但不申请内存
                    bmpFactoryOptions.inJustDecodeBounds = true;

                    // 计算缩放值
                    int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
                            / (float) dh);
                    int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                            / (float) dw);
                    //图片的宽高除以屏幕宽高，算出宽和高的缩放比例，取较大值作为图片的缩放比例
                    if (heightRatio > 1 && widthRatio > 1) {

                        if (heightRatio > widthRatio) {

                            bmpFactoryOptions.inSampleSize = heightRatio;
                        } else {
                            bmpFactoryOptions.inSampleSize = widthRatio;
                        }

                    }
                    //为图片申请内存
                    bmpFactoryOptions.inJustDecodeBounds = false;
                    // 得到缩放后的Bitmap对象
                    Bitmap bmp = BitmapFactory.decodeFile(path, bmpFactoryOptions);

                    Message message =new Message();
                    message.obj = bmp;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
