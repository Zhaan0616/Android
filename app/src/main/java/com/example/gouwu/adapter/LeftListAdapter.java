package com.example.gouwu.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gouwu.R;

import java.util.ArrayList;
import java.util.List;

public class LeftListAdapter extends RecyclerView.Adapter<LeftListAdapter.MyHolder> {
    private List<String> dataList=new ArrayList<>();
    private int currentIndex=0;
    public LeftListAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_list_item, null);
        return new MyHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //绑定数据
        String name = dataList.get(position);
        holder.tv_name.setText(name);
        //分类点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mLeftListOnClickItemListener){
                    mLeftListOnClickItemListener.onItemClick(position);
                }
            }
        });
        //
        if(currentIndex==position){
            holder.itemView.setBackgroundResource(R.drawable.type_selector_bg);
        }else{
            holder.itemView.setBackgroundResource(R.drawable.type_selector_normal_bg);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        //初始化控件
        TextView tv_name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.name);
        }
    }
    private  LeftListOnClickItemListener mLeftListOnClickItemListener;


    public void setmLeftListOnClickItemListener(LeftListOnClickItemListener mLeftListOnClickItemListener) {
        this.mLeftListOnClickItemListener = mLeftListOnClickItemListener;
    }

    //recyclerview点击事
    public interface LeftListOnClickItemListener{
        void onItemClick(int position);
    }
    public void setCurrentIndex(int position){
        this.currentIndex=position;
        //不能少
        notifyDataSetChanged();
    }
}
