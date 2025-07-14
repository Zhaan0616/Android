package com.example.gouwu.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gouwu.R;
import com.example.gouwu.adapter.OrderListAdapter;
import com.example.gouwu.db.OrderDbHelper;
import com.example.gouwu.entity.OrderInfo;
import com.example.gouwu.entity.ProductInfo;
import com.example.gouwu.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private OrderListAdapter mOrderListAdapter;

    private TextView btn_search;
    private EditText et_search;
    private List<OrderInfo> orderInfos = new ArrayList<>();
    private List<OrderInfo> searchList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order, container, false);

        //初始化控件
        recyclerView = rootView.findViewById(R.id.recyclerView);
        btn_search = rootView.findViewById(R.id.btn_search);
        et_search = rootView.findViewById(R.id.et_search);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化mOrderListAdapter
        mOrderListAdapter = new OrderListAdapter();

        //设置OrderListAdapter
        recyclerView.setAdapter(mOrderListAdapter);

        //recyclerView点击事件
        mOrderListAdapter.setmOnItemClickListener(new OrderListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(OrderInfo orderInfo, int position) {
                new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("确定要删除订单吗").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int row = OrderDbHelper.getInstance(getActivity()).delete(orderInfo.getOrder_id() + "");
                        if (row > 0) {
                            loadData();
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
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
                    if (orderInfos.size() > 0) {
                        for (int i = 0; i < orderInfos.size(); i++) {
                            if (orderInfos.get(i).getProduct_title().contains(search_str)) {
                                searchList.add(orderInfos.get(i));
                            }
                        }
                    }

                    //刷新数据
                    mOrderListAdapter.setListData(searchList);
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
                if (TextUtils.isEmpty(s)) {
                    mOrderListAdapter.setListData(orderInfos);
                }
            }
        });
        //获取数据
        loadData();
    }

    public void loadData() {
        //获取订单数据
        UserInfo userInfo = UserInfo.getsUserInfo();
        if (null != userInfo) {
            orderInfos = OrderDbHelper.getInstance(getActivity()).queryOrderListData(userInfo.getUsername());
            mOrderListAdapter.setListData(orderInfos);
        }
    }


}