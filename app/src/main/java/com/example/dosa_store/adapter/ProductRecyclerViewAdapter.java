package com.example.dosa_store.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.dosa_store.ProductDetailActivity;
import com.example.dosa_store.R;
import com.example.dosa_store.model.Product;


import java.util.ArrayList;


public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {
    private static ArrayList<Product> productList;

    public ProductRecyclerViewAdapter(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.id.setText(Integer.toString(product.getId()));
        holder.name.setText(product.getName());
        Glide.with(holder.itemView).load(product.getImgUrl()).centerCrop().into(holder.img);
        holder.description.setText(product.getDescription());
        holder.itemView.setOnClickListener(v->{
            Intent intent=new Intent(holder.itemView.getContext(), ProductDetailActivity.class);
            intent.putExtra("id",productList.get(position).getId());
            intent.putExtra("name",productList.get(position).getName());
            intent.putExtra("image",productList.get(position).getImgUrl());
            intent.putExtra("description",productList.get(position).getDescription());
            intent.putExtra("price",productList.get(position).getPrice());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        ImageView img;

        TextView name;
        TextView description;


        public ViewHolder(View itemView) {
            super(itemView);
            id= itemView.findViewById(R.id.item_id);
            img= itemView.findViewById(R.id.item_image);
            name=itemView.findViewById(R.id.item_title);
            description=itemView.findViewById(R.id.item_description);
        }
    }
}