package com.example.gouwu.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.gouwu.entity.CarInfo;

import java.util.ArrayList;
import java.util.List;

public class CollectHelper extends SQLiteOpenHelper {
    private static CollectHelper sHelper;
    private static final String DB_NAME = "collect.db";   //数据库名
    private static final int VERSION = 1;    //版本号

    //必须实现其中一个构方法
    public CollectHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //创建单例，供使用调用该类里面的的增删改查的方法
    public synchronized static CollectHelper getInstance(Context context) {
        if (null == sHelper) {
            sHelper = new CollectHelper(context, DB_NAME, null, VERSION);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建collect_table表
        db.execSQL("create table collect_table(_id integer primary key autoincrement, " +
                "username text," +       //用户名
                "product_id integer," +
                "product_img integer," +
                "product_title text," +
                "product_price integer,"+
                "product_count integer" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //添加商品到收藏
    public int addCollect(String username, int product_id, int product_img,String product_title,int product_price) {
        //判断是否添加过商品，如果添加过，只需修改商品的数量
        CarInfo addCar = isAddCollect(username, product_id);
        if(addCar==null){
            //获取SQLiteDatabase实例
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            //填充占位符
            values.put("username", username);
            values.put("product_id", product_id);
            values.put("product_img", product_img);
            values.put("product_title", product_title);
            values.put("product_price", product_price);
            values.put("product_count",1);
            String nullColumnHack = "values(null,?,?,?,?,?,?)";
            //执行
            int insert = (int) db.insert("collect_table", nullColumnHack, values);
            //db.close();
            return insert;
        }else{
            return updateCollect(addCar.getCar_id(),addCar);
        }
    }

    //修改收藏
    public int updateCollect(int car_id, CarInfo carInfo) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        // 填充占位符
        ContentValues values = new ContentValues();
        values.put("product_count", carInfo.getProduct_count()+1);
        // 执行SQL
        int update = db.update("collect_table", values, " _id=?", new String[]{car_id+""});
        // 关闭数据库连接
        //db.close();
        return update;

    }
    //根据用户名和商品id判断有没有添加过商品到收藏
    @SuppressLint("Range")
    public CarInfo isAddCollect(String username,int product_id) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getReadableDatabase();
        CarInfo CarInfo = null;
        String sql = "select _id,username,product_id,product_img,product_title,product_price,product_count from collect_table where username=? and product_id=?";
        String[] selectionArgs = {username,product_id+""};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToNext()) {
            int car_id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("username"));
            int productId = cursor.getInt(cursor.getColumnIndex("product_id"));
            int product_img = cursor.getInt(cursor.getColumnIndex("product_img"));
            String product_title = cursor.getString(cursor.getColumnIndex("product_title"));
            int product_price = cursor.getInt(cursor.getColumnIndex("product_price"));
            int product_count = cursor.getInt(cursor.getColumnIndex("product_count"));
            CarInfo = new CarInfo(car_id, name, productId,product_img, product_title,product_price,product_count);
        }
        cursor.close();
        //db.close();
        return CarInfo;
    }

    //根据用户名查询收藏
    @SuppressLint("Range")
    public List<CarInfo> queryCarList(String username) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getReadableDatabase();
        List<CarInfo> list = new ArrayList<>();
        String sql = "select _id,username,product_id,product_img,product_title,product_price,product_count from collect_table where username=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            int car_id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("username"));
            int productId = cursor.getInt(cursor.getColumnIndex("product_id"));
            int product_img = cursor.getInt(cursor.getColumnIndex("product_img"));
            String product_title = cursor.getString(cursor.getColumnIndex("product_title"));
            int product_price = cursor.getInt(cursor.getColumnIndex("product_price"));
            int product_count = cursor.getInt(cursor.getColumnIndex("product_count"));
            list.add(new CarInfo(car_id, name, productId,product_img, product_title,product_price,product_count));
        }
        cursor.close();
        //db.close();
        return list;
    }

    //删除收藏商品
    public int delete(String car_id) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        // 执行SQL
        int delete = db.delete("collect_table", " _id=?", new String[]{car_id});
        // 关闭数据库连接
        //db.close();
        return delete;
    }

}