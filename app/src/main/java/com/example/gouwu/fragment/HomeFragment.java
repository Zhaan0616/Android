package com.example.gouwu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gouwu.ProductDetalisActivity;
import com.example.gouwu.R;
import com.example.gouwu.adapter.LeftListAdapter;
import com.example.gouwu.adapter.RightListAdapter;
import com.example.gouwu.entity.BannerDataInfo;
import com.example.gouwu.entity.DataSrevice;
import com.example.gouwu.entity.ProductInfo;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private View rootView;
    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;
    private LeftListAdapter mLeftListAdapter;
    private RightListAdapter mRightListAdapter;
    private List<String> leftDataList = new ArrayList<>();

    private TextView btn_search;
    private EditText et_search;

    private Banner banner;
    private List<BannerDataInfo> mBannerDataInfoList = new ArrayList<>();

    private List<ProductInfo> mProductInfoList = new ArrayList<>();
    private List<ProductInfo> searchList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //初始化控件
        leftRecyclerView = rootView.findViewById(R.id.leftRecyclerView);
        rightRecyclerView = rootView.findViewById(R.id.rightRecyclerView);
        banner = rootView.findViewById(R.id.banner);
        btn_search = rootView.findViewById(R.id.btn_search);
        et_search = rootView.findViewById(R.id.et_search);
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        leftDataList.add("电子产品");
        leftDataList.add("沙发");
        leftDataList.add("鞋包");
        leftDataList.add("饰品");
        leftDataList.add("女装");
        leftDataList.add("杯子");


        //添加banner数据
        mBannerDataInfoList.add(new BannerDataInfo(R.drawable.iphon, ""));
        mBannerDataInfoList.add(new BannerDataInfo(R.drawable.shafa, ""));
        mBannerDataInfoList.add(new BannerDataInfo(R.drawable.qunzi, ""));
        mBannerDataInfoList.add(new BannerDataInfo(R.drawable.bao, ""));
        mBannerDataInfoList.add(new BannerDataInfo(R.drawable.beizi, ""));
        //设置适配器
        banner.setAdapter(new BannerImageAdapter<BannerDataInfo>(mBannerDataInfoList) {

            @Override
            public void onBindView(BannerImageHolder holder, BannerDataInfo data, int position, int size) {
                //设置数据
                holder.imageView.setImageResource(data.getImg());
            }
        });
        banner.setAdapter(new BannerImageAdapter<BannerDataInfo>(mBannerDataInfoList) {


            @Override
            public void onBindView(BannerImageHolder bannerImageHolde, BannerDataInfo bannerDataInfo, int position, int size) {
                //设置数据
                bannerImageHolde.imageView.setImageResource(bannerDataInfo.getImg());
            }
        });

        //添加生命周期观察者
        banner.addBannerLifecycleObserver(this);
        banner.setBannerGalleryEffect(10,10);
        //设置指示器
        banner.setIndicator(new CircleIndicator(getActivity()));

        //初始化控件
        mLeftListAdapter = new LeftListAdapter(leftDataList);
        leftRecyclerView.setAdapter(mLeftListAdapter);

        mRightListAdapter = new RightListAdapter();
        rightRecyclerView.setAdapter(mRightListAdapter);
        //默认加载食品数据
        mProductInfoList = DataSrevice.getListData(0);
        mRightListAdapter.setListData(mProductInfoList);

        //recyclerview点击事件
        mRightListAdapter.setmOnItemClickListener(new RightListAdapter.onItemClickListener() {
            @Override
            public void onItemOnclick(ProductInfo productInfo, int position) {
                //跳转传值
                Intent intent = new Intent(getActivity(), ProductDetalisActivity.class);
                //intent 传递对象的时候，ProductInfo实体类要实现implements Serializable
                intent.putExtra("productInfo", productInfo);
                startActivity(intent);

            }
        });

        //recyclerview点击事件
        mLeftListAdapter.setmLeftListOnClickItemListener(new LeftListAdapter.LeftListOnClickItemListener() {
            @Override
            public void onItemClick(int position) {
                mLeftListAdapter.setCurrentIndex(position);
                //点击左侧分类切换对应的数据
                mProductInfoList.clear();
                mProductInfoList = DataSrevice.getListData(position);
                mRightListAdapter.setListData(mProductInfoList);
            }
        });

        //搜索
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchList.clear();
                String search_str = et_search.getText().toString();
                if (TextUtils.isEmpty(search_str)) {
                    Toast.makeText(getActivity(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i <mProductInfoList.size() ; i++) {
                        if (mProductInfoList.get(i).getProduct_title().contains(search_str)){
                            searchList.add(mProductInfoList.get(i));
                        }
                    }
                    //刷新数据
                    mRightListAdapter.setListData(searchList);
                }
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (TextUtils.isEmpty(s)){
                    mRightListAdapter.setListData(mProductInfoList);
                }
            }
        });
    }
}