package com.example.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.Product;

public class DetailActivity extends AppCompatActivity {
    private TextView tvDetailTitle;
    private TextView tvDetailDescription;
    private TextView tvDetailPrice;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvDetailTitle=findViewById(R.id.tv_detail_title);
        tvDetailPrice=findViewById(R.id.tv_detail_price);
        tvDetailDescription=findViewById(R.id.tv_detail_description);
        mImage=findViewById(R.id.iv_detail_image);
        //传入product信息
        tvDetailTitle.setText(getIntent().getStringExtra("title"));
        tvDetailPrice.setText(getIntent().getStringExtra("price"));
        tvDetailDescription.setText(getIntent().getStringExtra("description"));
        String imageUriString = getIntent().getStringExtra("image_uri");
        if (imageUriString != null && !imageUriString.isEmpty()) {
            Glide.with(this)
                    .load(imageUriString)
                    .into(mImage);
        }
        //设置返回键
        Button btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this,HomeActivity.class));
            }
        });
        //设置购买按钮de监听器
        Button btn_buy=findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this,"购买成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}