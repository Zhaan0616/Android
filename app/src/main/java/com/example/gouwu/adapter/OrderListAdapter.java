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
import com.example.gouwu.entity.OrderInfo;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyHolder> {
    private List<OrderInfo> mOrderInfos =new ArrayList<>();

    public void setListData(List<OrderInfo> list){
        this.mOrderInfos=list;
        //刷新
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, null);
        return new MyHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //绑定数据
        OrderInfo orderInfo = mOrderInfos.get(position);

        //设置数据
        holder.product_count.setText("x"+orderInfo.getProduct_count());
        holder.product_img.setImageResource(orderInfo.getProduct_img());
        holder.product_title.setText(orderInfo.getProduct_title());
        holder.product_price.setText(orderInfo.getProduct_price()+"");

        //设置地址
        holder.address.setText("地址【"+orderInfo.getAddress()+"】;联系方式"+orderInfo.getMobile());

        //长按删除订单
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onItemClick(orderInfo,position);
                }
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mOrderInfos.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        ImageView product_img;
        TextView product_title;
        TextView product_price;
        TextView product_count;
        TextView address;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            product_img=itemView.findViewById(R.id.product_img);
            product_title=itemView.findViewById(R.id.product_title);
            product_price=itemView.findViewById(R.id.product_price);
            product_count=itemView.findViewById(R.id.product_count);
            address=itemView.findViewById(R.id.address);
        }
    }

    private onItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(OrderInfo orderInfo,int position);
    }


}
