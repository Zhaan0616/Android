package com.example.gouwu.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.gouwu.AboutActivity;
import com.example.gouwu.CollectActivity;
import com.example.gouwu.LoginActivity;
import com.example.gouwu.NoticeActivity;
import com.example.gouwu.PrivateActivity;
import com.example.gouwu.R;
import com.example.gouwu.UpdatePasswordActivity;
import com.example.gouwu.db.UserDbHelper;
import com.example.gouwu.entity.UserInfo;
import com.example.gouwu.util.SelectPicture;
import com.google.android.material.navigation.NavigationView;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;

public class MineFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private View rootView;
    private TextView tv_username;
    private TextView tv_nickname;
    private ImageView iv_head;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        drawerLayout=rootView.findViewById(R.id.drawer_layout);
        NavigationView navigationView = rootView.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_call);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });


        //初始化控件
        tv_username = rootView.findViewById(R.id.tv_username);
        tv_nickname = rootView.findViewById(R.id.tv_nickname);
        iv_head = rootView.findViewById(R.id.iv_head);
        UserInfo userInfo = UserInfo.getsUserInfo();
        if(TextUtils.isEmpty(userInfo.getPic())){
            Glide.with(getActivity()).load(R.drawable.tx).into(iv_head);
        }else{
            Glide.with(getActivity()).load(userInfo.getPic()).into(iv_head);
        }
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(getActivity())
                        .hasShadowBg(true)
                        .asBottomList("", new String[]{"相册", "拍照"},
                                (position, text) -> {
                                    SelectPicture.takePic(getActivity(), position != 0, 1, new OnResultCallbackListener<LocalMedia>() {
                                        @Override
                                        public void onResult(ArrayList<LocalMedia> result) {
                                            Glide.with(getActivity()).load(result.get(0).getRealPath()).into(iv_head);

                                            if(null!=userInfo){
                                                int row = UserDbHelper.getInstance(getActivity()).updatePic(userInfo.getUsername(), result.get(0).getRealPath());
                                                if(row>0){
                                                    Toast.makeText(getActivity(), "头像修改成功", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(getActivity(), "头像修改失败", Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                        }

                                        @Override
                                        public void onCancel() {

                                        }
                                    });
                                }).show();
            }
        });
        //退出登录
        rootView.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("温馨提示").setMessage("确定要退出登录吗").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //退出登录逻辑
                                getActivity().finish();

                                //启动登录页面
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        })

                        .show();
            }
        });

        rootView.findViewById(R.id.updatePwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改密码
                Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                startActivityForResult(intent, 1000);  //回传销毁页面
            }
        });

        // 我的收藏
        rootView.findViewById(R.id.rl_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
            }
        });

        //关于App
        rootView.findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });


        //系统通知
        rootView.findViewById(R.id.notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });
        //隐私政策
        rootView.findViewById(R.id.mine_private).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PrivateActivity.class);
                startActivity(intent);
            }
        });


        //联系客服
        rootView.findViewById(R.id.service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity()).setTitle("联系客服").setMessage("3259370637").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent dialIntent = new Intent(Intent.ACTION_CALL);
                            dialIntent.setData(Uri.parse("tel:191-xxxx-xxxx"));
                            startActivity(dialIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //设置用户数据
        UserInfo userInfo = UserInfo.getsUserInfo();
        if (null != userInfo) {
            tv_username.setText(userInfo.getUsername());
            tv_nickname.setText(userInfo.getNickname());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1000) {
            getActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }
}