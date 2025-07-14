package com.example.gouwu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gouwu.adapter.CarListAdapter;
import com.example.gouwu.adapter.CollectListAdapter;
import com.example.gouwu.db.CarDbHelper;
import com.example.gouwu.db.CollectHelper;
import com.example.gouwu.entity.CarInfo;
import com.example.gouwu.entity.UserInfo;

import java.util.List;

public class CollectActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private CollectListAdapter collectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        //返回
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        collectListAdapter = new CollectListAdapter();
        //设置适配器
        recyclerView.setAdapter(collectListAdapter);
        collectListAdapter.setmOnItemClickListener(new CollectListAdapter.onItemClickListener() {
            @Override
            public void itemClick(CarInfo carInfo, int position) {

            }

            @Override
            public void delOnClick(CarInfo carInfo, int position) {
                new AlertDialog.Builder(CollectActivity.this)
                        .setTitle("温馨提示")
                        .setMessage("确认是否要取消收藏该商品")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CollectHelper.getInstance(CollectActivity.this).delete(carInfo.getCar_id() + "");
                                loadData();
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
        loadData();
    }

    public void loadData() {
        UserInfo userInfo = UserInfo.getsUserInfo();
        if (null != userInfo) {
            List<CarInfo> carList = CollectHelper.getInstance(CollectActivity.this).queryCarList(userInfo.getUsername());
            collectListAdapter.setCarInfoList(carList);
        }
    }
}