package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.manager.SessionManager;

public class LoginActivity extends AppCompatActivity {
    EditText et_password;
    EditText et_name;
    Button btu_login;
    CheckBox cb_remember;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_password = findViewById(R.id.et_password);
        et_name = findViewById(R.id.et_name);
        btu_login = findViewById(R.id.btu_login);
        cb_remember = findViewById(R.id.cb_remember);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password", false);
        //勾选了记住密码就把数据填入
        if (isRemember) {
            et_name.setText(pref.getString("name", ""));
            et_password.setText(pref.getString("password", ""));
            cb_remember.setChecked(true);
        }
        //设置登录按钮监听器
        btu_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = pref.edit();
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                if (password.length() < 8) {
                    Toast.makeText(LoginActivity.this, "密码至少输入八位", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (cb_remember.isChecked()) {//检查是否勾选记住密码
                        editor.putBoolean("remember_password", true);
                        editor.putString("name", name);
                        editor.putString("password", password);
                    } else {
                        editor.clear();//没勾选就清空
                    }
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //标记已登录
                    SessionManager sessionManager = new SessionManager(LoginActivity.this);
                    sessionManager.setLogin(true);
                    //跳转主页面
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
