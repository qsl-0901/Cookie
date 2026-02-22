package com.example.myapplication.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.bean.Product;
import com.google.gson.Gson;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddActivity extends AppCompatActivity {
    public static final int CHOOSE_PHOTO=2;
    private String path;
    EditText et_title;
    EditText et_description;
    EditText et_price;
    ImageView user_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //返回主页
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this, HomeActivity.class));
            }
        });
        //添加图片
        user_image = findViewById(R.id.iv_user_image);
        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查是否有权限
                if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.READ_MEDIA_IMAGES) !=
                        PackageManager.PERMISSION_GRANTED) {
                    //没有权限申请权限
                    ActivityCompat.requestPermissions(AddActivity.this,
                            new String[]{Manifest.permission.READ_MEDIA_IMAGES
                            }, 1);
                } else {
                    //有权限就打开相册
                    openAlbum();
                }
            }
        });
        //发布商品
        Button btn_sale = findViewById(R.id.btn_sale);
        btn_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_title = findViewById(R.id.et_title);
                et_description = findViewById(R.id.et_description);
                et_price = findViewById(R.id.et_price);
                String title = et_title.getText().toString();
                String description = et_description.getText().toString();
                String price = et_price.getText().toString();
                if (price.isEmpty() || description.isEmpty()) {
                    Toast.makeText(AddActivity.this, "描述和价格必须有哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("user_title",title);
                intent.putExtra("user_description",description);
                intent.putExtra("user_price",price);
                intent.putExtra("path",path);
                setResult(RESULT_OK, intent);
                Toast.makeText(AddActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }

    //打开相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");//筛选图片
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    //处理权限申请回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();//成功就打开相册
            } else {
                Toast.makeText(AddActivity.this, "请求权限失败", Toast.LENGTH_SHORT).show();
                ;
            }
        }
    }

    //选择图片时
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RESULT_DEBUG", "data = " + data);
        if (requestCode == CHOOSE_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                path = uri.toString();
                display(Uri.parse(path));
            }
        }
    }
//获取真实的图片路径
    private String getPath(Uri uri,String selection) {
        String path="";
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return path;
    }
    //展示图片
    private void display(Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(uri)
            );
            user_image.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

}