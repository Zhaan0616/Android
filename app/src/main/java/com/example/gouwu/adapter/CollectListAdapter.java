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

public class CollectListAdapter extends RecyclerView.Adapter<CollectListAdapter.MyHolder> {

    private List<CarInfo> mCarInfoList=new ArrayList<>();

    public void setCarInfoList(List<CarInfo> list){
        this.mCarInfoList=list;
        //一定要调用
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CollectListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_list_item, null);
        return new CollectListAdapter.MyHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull CollectListAdapter.MyHolder holder, int position) {

        //绑定数据
        CarInfo carInfo = mCarInfoList.get(position);
        holder.product_img.setImageResource(carInfo.getProduct_img());
        holder.product_title.setText(carInfo.getProduct_title());
        holder.product_price.setText(carInfo.getProduct_price()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.itemClick(carInfo,position);
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

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            product_img=itemView.findViewById(R.id.product_img);
            product_title=itemView.findViewById(R.id.product_title);
            product_price=itemView.findViewById(R.id.product_price);
        }
    }

    private CollectListAdapter.onItemClickListener mOnItemClickListener;



    public void setmOnItemClickListener(CollectListAdapter.onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface onItemClickListener{
        void itemClick(CarInfo carInfo,int position);
        void delOnClick(CarInfo carInfo,int position);
    }
}
