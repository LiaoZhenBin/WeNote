package com.example.liaozhenbin.wenote.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.liaozhenbin.wenote.R;
import com.example.liaozhenbin.wenote.activity.MainActivity;
import com.example.liaozhenbin.wenote.activity.ShowActivity;
import com.example.liaozhenbin.wenote.adapter.PhotoAdapter;
import com.example.liaozhenbin.wenote.db.DBManager;
import com.example.liaozhenbin.wenote.domain.MyNote;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liaozhenbin on 2016/1/6.
 */
public class WriteFragment extends Fragment implements View.OnClickListener {
    private EditText et_title;
    private EditText et_content;
    private DBManager manager;
    private SharedPreferences preferences;
    private GridView gridView;
    private Button bt_photos;


    private static final int START_CAMERA_CODE = 0;
    private static final int START_ALBUM_CODE = 1;

    private String imageFilePath;
    private Uri imageUri;

    private int longItem;

    private List<String> photoList;
    private PhotoAdapter photoAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        et_title = (EditText) getActivity().findViewById(R.id.et_title);
        et_content = (EditText) getActivity().findViewById(R.id.et_content);
        gridView = (GridView) getActivity().findViewById(R.id.photosGrid);

        getActivity().findViewById(R.id.bt_save).setOnClickListener(this);
        bt_photos = (Button) getActivity().findViewById(R.id.bt_photos);
        bt_photos.setOnClickListener(this);
        preferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);

        manager = new DBManager(getActivity());

        photoList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(getActivity(), R.layout.item_photo, photoList);

        gridView.setAdapter(photoAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showIntent = new Intent(getActivity(), ShowActivity.class);
                imageFilePath = photoList.get(position);
                showIntent.putExtra("path", imageFilePath);
                startActivity(showIntent);
            }
        });

        //获取长按Item的_id;
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longItem = position;
                return false;
            }
        });

        registerForContextMenu(gridView);

    }

    //当相机拍照和选择相册activity结束时回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case START_ALBUM_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bm = null;
                    // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                    ContentResolver resolver = getActivity().getContentResolver();
                    // 此处的用于判断接收的Activity是不是你想要的那个
                    try {
                        imageUri = data.getData(); // 获得图片的uri
                        // 显得到bitmap图片这里开始的第二部分，获取图片的路径：
                        bm = MediaStore.Images.Media.getBitmap(resolver, imageUri);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        // 好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = getActivity().managedQuery(imageUri, proj, null, null, null);
                        // 按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToNext();
                        // 最后根据索引值获取图片路径
                        imageFilePath = cursor.getString(column_index);

                        //PhotoShow.show(photo, imageFilePath);
                        photoList.add(imageFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case START_CAMERA_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    //PhotoShow.show(photo, imageFilePath);
                    photoList.add(imageFilePath);
                }
                break;
        }
        photoAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                final String author = preferences.getString("author", "");
                String title = et_title.getText().toString();
                String content = et_content.getText().toString();
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                    Toast.makeText(getActivity(), "Title or Content is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //获取照片地址
                String path;
                if (photoList.size() != 0) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < photoList.size(); i++) {
                        if (i != photoList.size() - 1) {
                            builder.append(photoList.get(i) + "$$$");
                        } else {
                            builder.append(photoList.get(i));
                        }
                    }
                    path = builder.toString();
                } else {
                    path = "";
                }

                manager.add(new MyNote(author, title, content, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis()), path));
                et_title.setText("");
                et_content.setText("");
                photoList.clear();
                photoAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                MainActivity activity = (MainActivity) getActivity();
                activity.hideFragments(transaction);
                if (activity.indexFragment != null) {
                    //如果indexFragment不为空，则创建一个并添加到界面上
                    transaction.remove(activity.indexFragment);
                }
                activity.indexFragment = new IndexFragment();
                transaction.add(R.id.fl_main, activity.indexFragment);

                transaction.commit();
                //设置第一个RadioButton被选中
                ((RadioButton) activity.findViewById(R.id.rb_index)).setChecked(true);
                break;

            case R.id.bt_photos://弹出ContextMenu
                registerForContextMenu(bt_photos);
                getActivity().openContextMenu(bt_photos);
                unregisterForContextMenu(bt_photos);
                break;

        }
    }

    //上下文菜单，本例会通过长按条目激活上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        switch (view.getId()) {
            //根据点击的view的添加菜单项
            case R.id.bt_photos:
                menu.add(0, 0, 0, "相机拍摄");
                menu.add(0, 1, 0, "手机相册");
                break;
            default:
                menu.add(0, 2, 0, "移除");
                menu.add(0, 3, 0, "取消");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0://相机拍照
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myphoto" + dateFormat.format(new Date(System.currentTimeMillis())) + ".jpg";
                File outputImage = new File(imageFilePath);
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, START_CAMERA_CODE);//启动相机
                break;
            case 1://手机相册
                Intent intent1 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, START_ALBUM_CODE);//启动选择相册
                break;
            case 2://移除照片
                photoList.remove(longItem);
                photoAdapter.notifyDataSetChanged();
                break;
            case 3://取消
                break;
        }
        return true;
    }
}