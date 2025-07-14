package com.example.gouwu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gouwu.db.CarDbHelper;
import com.example.gouwu.db.CollectHelper;
import com.example.gouwu.entity.CarInfo;
import com.example.gouwu.entity.ProductInfo;
import com.example.gouwu.entity.UserInfo;

public class ProductDetalisActivity extends AppCompatActivity {

    private ImageView product_img;
    private TextView product_title;
    private TextView product_price;
    private TextView product_details;
    private ProductInfo productInfo;
    private Button addCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detalis);

        //获取传递的数据
        productInfo = (ProductInfo) getIntent().getSerializableExtra("productInfo");

        //返回
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //初始化控件
        product_img = findViewById(R.id.product_img);
        product_title = findViewById(R.id.product_title);
        product_price = findViewById(R.id.product_price);
        product_details = findViewById(R.id.product_details);
        //设置数据
        if (null != productInfo) {
            product_img.setImageResource(productInfo.getProduct_img());
            product_title.setText(productInfo.getProduct_title());
            product_details.setText(productInfo.getProduct_details());
            product_price.setText(productInfo.getProduct_price() + "");
        }
        addCollect = findViewById(R.id.addCollect);
        UserInfo userInfo = UserInfo.getsUserInfo();
        CarInfo addCar = CollectHelper.getInstance(ProductDetalisActivity.this).isAddCollect(userInfo.getUsername(), productInfo.getProduct_id());
        if(addCar!=null){
            // 已收藏
            addCollect.setVisibility(View.GONE);
        }

        // 加入收藏
        addCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userInfo != null) {
                    if (addCollect.getText().toString().equals("收藏商品")) {
                        int row = CollectHelper.getInstance(ProductDetalisActivity.this).addCollect(userInfo.getUsername(), productInfo.getProduct_id(), productInfo.getProduct_img(), productInfo.getProduct_title(), productInfo.getProduct_price());
                        if (row > 0) {
                            Toast.makeText(ProductDetalisActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                            addCollect.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ProductDetalisActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        //加入购物车
        findViewById(R.id.addCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ProductDetalisActivity.this)
                        .setTitle("确认是否加入到购物车")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserInfo userInfo = UserInfo.getsUserInfo();
                                if (userInfo != null) {
                                    //加入到购物车
                                    int row = CarDbHelper.getInstance(ProductDetalisActivity.this).addCar(userInfo.getUsername(), productInfo.getProduct_id(), productInfo.getProduct_img(), productInfo.getProduct_title(), productInfo.getProduct_price());
                                    if (row > 0) {
                                        Toast.makeText(ProductDetalisActivity.this, "加入成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ProductDetalisActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });


    }
}