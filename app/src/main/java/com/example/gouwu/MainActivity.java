package com.example.gouwu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gouwu.entity.BannerDataInfo;
import com.example.gouwu.fragment.CarFragment;
import com.example.gouwu.fragment.HomeFragment;
import com.example.gouwu.fragment.MineFragment;
import com.example.gouwu.fragment.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private HomeFragment mHomeFragment;
    private CarFragment mCarFragment;
    private OrderFragment mOrderFragment;
    private MineFragment mMineFragment;
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        mBottomNavigationView=findViewById(R.id.bottomNavigationView);

        //设置点击事件
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home){
                    selectedFragment(0);

                }else if(item.getItemId()==R.id.car){
                    selectedFragment(1);

                }else if(item.getItemId()==R.id.order){
                    selectedFragment(2);

                }else{
                    selectedFragment(3);

                }
                return true;
            }
        });
        //默认首页选中
        selectedFragment(0);
    }
    private void selectedFragment(int position){
        androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if(position==0){ //第一个选中
            if(mHomeFragment==null){
                mHomeFragment=new HomeFragment();
                fragmentTransaction.add(R.id.content,mHomeFragment);
            }else{
                fragmentTransaction.show(mHomeFragment);
            }
        } else if(position==1){
            if(mCarFragment==null){
                mCarFragment=new CarFragment();
                fragmentTransaction.add(R.id.content,mCarFragment);
            }else{
                fragmentTransaction.show(mCarFragment);

                mCarFragment.loadData();
            }
        }else if(position==2){
            if(mOrderFragment==null){
                mOrderFragment=new OrderFragment();
                fragmentTransaction.add(R.id.content,mOrderFragment);
            }else{
                fragmentTransaction.show(mOrderFragment);
                mOrderFragment.loadData();
            }
        }else{
            if(mMineFragment==null){
                mMineFragment=new MineFragment();
                fragmentTransaction.add(R.id.content,mMineFragment);
            }else{
                fragmentTransaction.show(mMineFragment);
            }
        }
        //一定要提交
        fragmentTransaction.commit();
    }
    private void hideFragment(FragmentTransaction fragmentTransaction){
        if(mHomeFragment!=null){
            fragmentTransaction.hide(mHomeFragment);
        }
        if(mCarFragment!=null){
            fragmentTransaction.hide(mCarFragment);
        }if(mOrderFragment!=null){
            fragmentTransaction.hide(mOrderFragment);
        }if(mMineFragment!=null){
            fragmentTransaction.hide(mMineFragment);
        }
    }

}