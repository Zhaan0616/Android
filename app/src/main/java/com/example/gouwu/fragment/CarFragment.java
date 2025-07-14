package com.example.gouwu.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gouwu.R;
import com.example.gouwu.adapter.CarListAdapter;
import com.example.gouwu.db.CarDbHelper;
import com.example.gouwu.db.OrderDbHelper;
import com.example.gouwu.entity.CarInfo;
import com.example.gouwu.entity.UserInfo;

import java.util.List;

public class CarFragment extends Fragment {

    private View rootView;

    private TextView total;

    private Button btn_total;

    private RecyclerView recyclerView;

    private CarListAdapter mCarListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_car, container, false);

        //初始化控件
        recyclerView=rootView.findViewById(R.id.recyclerView);
        total=rootView.findViewById(R.id.total);
        btn_total=rootView.findViewById(R.id.btn_total);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化mCarListAdapter
        mCarListAdapter=new CarListAdapter();
        //设置适配器
        recyclerView.setAdapter(mCarListAdapter);

        //recyclerView点击事件
        mCarListAdapter.setmOnItemClickListener(new CarListAdapter.onItemClickListener() {
            @Override
            public void onPlusOnClick(CarInfo carInfo, int position) {
                //加
                CarDbHelper.getInstance(getActivity()).updateProduct(carInfo.getCar_id(),carInfo);
                loadData();
            }

            @Override
            public void onSubTractOnClick(CarInfo carInfo, int position) {
                //减
                CarDbHelper.getInstance(getActivity()).subStractupdateProduct(carInfo.getCar_id(),carInfo);
                loadData();
            }

            @Override
            public void delOnClick(CarInfo carInfo, int position) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("温馨提示")
                        .setMessage("确认是否要删除该商品")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CarDbHelper.getInstance(getActivity()).delete(carInfo.getCar_id()+"");
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

        //结算点击事件
        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //批量将购物车里面的数据添加到订单
                UserInfo userInfo = UserInfo.getsUserInfo();
                if(null!=userInfo){
                    List<CarInfo> carList = CarDbHelper.getInstance(getActivity()).queryCarList(userInfo.getUsername());
                   if(carList.size()==0){
                       Toast.makeText(getActivity(), "您还没有选择商品", Toast.LENGTH_SHORT).show();
                   }else{

                       View view = LayoutInflater.from(getActivity()).inflate(R.layout.pay_dialog_layout, null);

                       EditText et_mobile=view.findViewById(R.id.et_moblie);
                       EditText et_address=view.findViewById(R.id.et_address);
                       TextView tv_total=view.findViewById(R.id.tv_total);

                       //设置总价格
                       tv_total.setText(total.getText().toString());
                       //生成订单
                       new AlertDialog.Builder(getActivity())
                               .setTitle("支付温馨提示")
                               .setView(view)
                               .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               })

                               .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       String address=et_address.getText().toString();
                                       String mobile=et_mobile.getText().toString();
                                       if(TextUtils.isEmpty(address) || TextUtils.isEmpty(mobile)){
                                           Toast.makeText(getActivity(), "请填写完整信息", Toast.LENGTH_SHORT).show();
                                       }else{
                                           OrderDbHelper.getInstance(getActivity()).insertByAll(carList,address,mobile);

                                            //清空购物车
                                            for(int i=0;i<carList.size();i++){
                                            CarDbHelper.getInstance(getActivity()).delete(carList.get(i).getCar_id()+"");
                                            }
                                            //重新加载数据
                                            loadData();
                                           Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                                       }

                                   }
                               })
                               .show();

                   }
                }
            }
        });

        loadData();

    }

    private void setTotalData(List<CarInfo> list){
        int toTalCount=0;
        for (int i=0;i<list.size();i++){
            int price=list.get(i).getProduct_price()*list.get(i).getProduct_count();

            toTalCount=toTalCount+price;
        }
        //设置数据
        total.setText(toTalCount+".00");
    }

    public void loadData(){

        UserInfo userInfo = UserInfo.getsUserInfo();
        if(null!=userInfo){
            //获取数据
            List<CarInfo> carList = CarDbHelper.getInstance(getActivity()).queryCarList(userInfo.getUsername());

            //设置数据
            mCarListAdapter.setCarInfoList(carList);

            //计算总价格
            setTotalData(carList);
        }
    }
}