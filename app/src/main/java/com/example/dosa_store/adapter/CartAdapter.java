package com.example.dosa_store.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dosa_store.R;
import com.example.dosa_store.model.CartItem;
import com.example.dosa_store.model.Product;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    ArrayList<CartItem> arr;
    public CartAdapter(ArrayList<CartItem> arr){
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
            view= View.inflate(parent.getContext(), R.layout.cart_item,null);
        }else{
            view=convertView;
        }
        CartItem cartItem=arr.get(position);
        ((TextView)view.findViewById(R.id.cart_item_id)).setText(Integer.toString(cartItem.getId()));
        ((TextView)view.findViewById(R.id.cart_item_title)).setText(cartItem.getName());
        ((TextView)view.findViewById(R.id.cart_item_quantity)).setText(Integer.toString(cartItem.getQuantity()));
        ((TextView)view.findViewById(R.id.cart_item_price)).setText(Float.toString(cartItem.getPrice()));
        return view;
    }
}
