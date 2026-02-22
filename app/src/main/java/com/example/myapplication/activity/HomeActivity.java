package com.example.myapplication.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.bean.Product;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView mRv;
    OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private static final int ADD_PRODUCT_REQUEST = 1;
    ArrayList<Product> products = new ArrayList<>();

    private ProductAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initRv();
        AddProducts();
        //点击“我的”跳转
        Button btu_my=findViewById(R.id.btu_my);
        btu_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MyActivity.class));
            }
        });
        //点击加号跳转添加商品页面
        Button btu_add=findViewById(R.id.btu_add);
        btu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(HomeActivity.this, AddActivity.class),1);
            }
        });
        //暂未开放的搜索功能，，
        Button btn_search=findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this,"该功能暂未开发",Toast.LENGTH_SHORT).show();
            }
        });

    }
    // 接收返回的商品
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode == RESULT_OK) {
            // 获取新商品
            Product newProduct = new Product(data.getStringExtra("user_title"), data.getStringExtra("user_description"),
                    data.getStringExtra("user_price"), Uri.parse(data.getStringExtra("path")));
            products.add(0,newProduct);
            adapter.notifyItemInserted(0);
            mRv.scrollToPosition(0);
        }}
    //添加本地商品
     public ArrayList<Product> initProducts() {
        products.add(new Product("我鸟都不鸟你", "表情玩偶鸟鸟都不鸟你毛绒玩具蓝鸟", "14.99", R.drawable.ic_bird));
        products.add(new Product("逆水寒成品号", "逆水寒白发天赏石成品号高评分", "185", R.drawable.ic_cold));
        products.add(new Product("王者荣耀V10账号", "王者荣耀V10便宜震我赫兹花和斗幻阙歌", "1500", R.drawable.ic_fight));
        /*products.add(new Product("老凤祥四叶草手绳", "足银四叶草手绳设计精致新年礼物春节礼盒送爱人", "33.91", R.drawable.ic_bracelet));
        products.add(new Product("AFK惊喜盒子", "AFK散粉唇泥唇釉护手霜睫毛膏粉底液", "9.9", R.drawable.ic_akf));
        products.add(new Product("红油笋丝", "香辣红油笋丝小包装开胃解馋", "10.1/斤", R.drawable.ic_bamboo));
        products.add(new Product("阿宽耗子", "阿宽爱吃鬼一根粉耗子甜辣味超长土豆粉条", "24.9/6袋", R.drawable.ic_powder));*/
        return products;
    }
    //传入adapter
    public void initRv(){
        mRv=findViewById(R.id.rv_product);
        initProducts();
        adapter=new ProductAdapter(this,products);
        mRv.setAdapter(adapter);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        //点击跳转详情页
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
            Intent intent=new Intent(HomeActivity.this, DetailActivity.class);
           //传入产品信息到详情页 试着运行时发现如果直接传入整个product会闪退好像是uri的缘故
                intent.putExtra("title", product.getTitle());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                if (product.getImage() != null) {
                    intent.putExtra("image_uri", product.getImage().toString());
                }
            startActivity(intent);
            }
        });
    }
    //这部分是通过网络请求实现的商品创建
    protected void AddProducts(){
        new Thread(()->{
            try{
                Request request = new Request.Builder()
                        .url("https://gist.githubusercontent.com/qsl-0901/0d55f84dba44963ad769d78f9edf35a9/raw/74f5165c9446e02edf102078e4e866bd10ba35a1/products.json")
                        .build();
                Response response=client.newCall(request).execute();
                String json=response.body().string();
                JsonArray jsonArray=gson.fromJson(json, JsonArray.class);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject item=jsonArray.get(i).getAsJsonObject();
                    String title = item.get("title").getAsString();
                    String desc = item.get("description").getAsString();
                    String price = item.get("price").getAsString();
                    String imageUrl = item.get("imageUrl").getAsString();
                    Product product = new Product(title, desc, price, Uri.parse(imageUrl));
                    runOnUiThread(() -> {
                        products.add(0,product);
                        adapter.notifyItemInserted(0);
                        mRv.scrollToPosition(0);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(HomeActivity.this, "错误：" + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        }
                ).start();
    }

}

