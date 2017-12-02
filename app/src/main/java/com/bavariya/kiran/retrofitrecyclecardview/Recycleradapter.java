package com.bavariya.kiran.retrofitrecyclecardview;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class Recycleradapter extends RecyclerView.Adapter<Recycleradapter.MyHolder>{

    List<Product> list;
    ImageLoader imageLoader;
    public Recycleradapter(List<Product> list, ImageLoader imageLoader) {
        this.list = list;
        this.imageLoader=imageLoader;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Product product = list.get(position);
        holder.name.setText(product.getProductname());
        holder.color.setText(product.getColor());
        String image1 = product.getImageurl();
        imageLoader.displayImage(image1, holder.image);
        holder.price.setText("INR "+product.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name, color, price;
        ImageView image;


        public MyHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.productimage);
            name = itemView.findViewById(R.id.name);
            color = itemView.findViewById(R.id.color);
            price = itemView.findViewById(R.id.price);

        }
    }
}