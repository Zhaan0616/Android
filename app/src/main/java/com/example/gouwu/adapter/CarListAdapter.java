package com.example.gouwu.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gouwu.R;
import com.example.gouwu.entity.CarInfo;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyHolder> {

    private List<CarInfo> mCarInfoList=new ArrayList<>();

    public void setCarInfoList(List<CarInfo> list){
        this.mCarInfoList=list;
        //一定要调用
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, null);
        return new MyHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //绑定数据
        CarInfo carInfo = mCarInfoList.get(position);
        holder.product_img.setImageResource(carInfo.getProduct_img());
        holder.product_title.setText(carInfo.getProduct_title());
        holder.product_price.setText(carInfo.getProduct_price()+"");
        holder.product_count.setText(carInfo.getProduct_count()+"");

        //点击事件
        holder.btn_substract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onSubTractOnClick(carInfo,position);
                }
            }
        });

        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onPlusOnClick(carInfo,position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.delOnClick(carInfo,position);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCarInfoList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        ImageView product_img;
        TextView product_title;
        TextView product_price;
        TextView product_count;
        TextView btn_substract;
        TextView btn_plus;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            product_img=itemView.findViewById(R.id.product_img);
            product_title=itemView.findViewById(R.id.product_title);
            product_price=itemView.findViewById(R.id.product_price);
            product_count=itemView.findViewById(R.id.product_count);
            btn_substract=itemView.findViewById(R.id.btn_substract);
            btn_plus=itemView.findViewById(R.id.btn_plus);
        }
    }

    private onItemClickListener mOnItemClickListener;



    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface onItemClickListener{
        void onPlusOnClick(CarInfo carInfo,int position);
        void onSubTractOnClick(CarInfo carInfo,int position);
        void delOnClick(CarInfo carInfo,int position);
    }
}
