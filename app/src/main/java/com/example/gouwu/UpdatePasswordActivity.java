package com.example.gouwu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gouwu.db.UserDbHelper;
import com.example.gouwu.entity.UserInfo;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText et_new_password;
    private EditText et_confirm_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        //初始化控件
        et_new_password=findViewById(R.id.et_new_password);
        et_confirm_password=findViewById(R.id.et_confirm_password);

        //修改密码点击事件
        findViewById(R.id.btn_update_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取文本
                String new_pwd=et_new_password.getText().toString();
                String confirm_pwd=et_confirm_password.getText().toString();

                if(TextUtils.isEmpty(new_pwd) || TextUtils.isEmpty(confirm_pwd)){
                    Toast.makeText(UpdatePasswordActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                }else if(!new_pwd.equals(confirm_pwd)){
                    Toast.makeText(UpdatePasswordActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                }else{

                    UserInfo userInfo = UserInfo.getsUserInfo();
                    if(null!=userInfo){
                        int row = UserDbHelper.getInstance(UpdatePasswordActivity.this).updatePwd(userInfo.getUsername(), new_pwd);
                        if(row>0){
                            Toast.makeText(UpdatePasswordActivity.this, "修改密码成功,请重新登录", Toast.LENGTH_SHORT).show();

                            //回传时调用startActivity启动一个页面，修改密码
                            setResult(1000);
                            finish(); //销毁页面
                        }else {
                            Toast.makeText(UpdatePasswordActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            }
        });

        //返回
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });










    }
}