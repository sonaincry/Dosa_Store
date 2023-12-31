package com.example.dosa_store.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dosa_store.MainActivity;
import com.example.dosa_store.R;
import com.example.dosa_store.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    ArrayList<Product> arr;
    public ProductAdapter(ArrayList<Product> arr){
        this.arr=arr;
    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null){
            view= View.inflate(parent.getContext(), R.layout.product_item,null);
        }else{
            view=convertView;
        }
        Product product=arr.get(position);
        ((TextView)view.findViewById(R.id.item_id)).setText(Integer.toString(product.getId()));
        ImageView imageView= view.findViewById(R.id.item_image);
        Glide.with(view).load(product.getImgUrl()).centerCrop().into(imageView);
        ((TextView)view.findViewById(R.id.item_title)).setText(product.getName());
        ((TextView)view.findViewById(R.id.item_description)).setText(product.getDescription());

        return view;
    }
}
