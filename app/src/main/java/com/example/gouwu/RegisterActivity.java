package com.example.gouwu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gouwu.db.UserDbHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化控件
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);

        //返回
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不要跳转到登录页面，直接把当前页面销毁
                finish();
            }
        });

        //点击注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=et_username.getText().toString();
                String password=et_password.getText().toString();
                if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }else{
                    int row = UserDbHelper.getInstance(RegisterActivity.this).register(username, password, "为发财而努力");
                   if(row>0){
                       //一定要提交
                       Toast.makeText(RegisterActivity.this, "注册成功,请登录", Toast.LENGTH_SHORT).show();
                       finish();//销毁当前页面
                   }
                }
            }
        });
    }
}