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
import com.example.gouwu.entity.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class RightListAdapter extends RecyclerView.Adapter<RightListAdapter.MyHolder>{
    private List<ProductInfo> mProductInfo=new ArrayList<>();

    public void setListData(List<ProductInfo> list){

        this.mProductInfo=list;
        //一定要刷新
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_list_item, null);
        return new MyHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //绑定数据
        ProductInfo productInfo = mProductInfo.get(position);

        holder.product_img.setImageResource(productInfo.getProduct_img());
        holder.product_title.setText(productInfo.getProduct_title());
        holder.product_details.setText(productInfo.getProduct_details());
        //只能设置String类型
        holder.product_price.setText(productInfo.getProduct_price()+"");
        //注册点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mOnItemClickListener){
                    mOnItemClickListener.onItemOnclick(productInfo,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductInfo.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView product_img;
        TextView product_title;
        TextView product_details;
        TextView product_price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            product_img=itemView.findViewById(R.id.product_img);
            product_title=itemView.findViewById(R.id.product_title);
            product_details=itemView.findViewById(R.id.product_details);
            product_price=itemView.findViewById(R.id.product_price);
        }
    }
    private onItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //详情点击事件
    public interface onItemClickListener{

        void onItemOnclick(ProductInfo productInfo,int position);
    }

}
