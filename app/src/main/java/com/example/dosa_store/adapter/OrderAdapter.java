package com.example.dosa_store.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dosa_store.OrderActivity;
import com.example.dosa_store.R;
import com.example.dosa_store.model.OrderDto;
import com.example.dosa_store.model.User;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<OrderDto> orderList;

    public OrderAdapter(ArrayList<OrderDto> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDto order = orderList.get(position);

        holder.tvNote.setText(order.getNote());
        holder.tvTotal.setText(Integer.toString(order.getTotal()));
        holder.tvOrderId.setText(Integer.toString(order.getId()));
        holder.tvOrderDate.setText(order.getOrderDate());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNote;
        TextView tvOrderDate;
        TextView tvTotal;
        TextView tvOrderId;


        public ViewHolder(View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvOrderId=itemView.findViewById(R.id.tvOrderId);
        }
    }
}
