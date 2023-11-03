package com.example.dosa_store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dosa_store.R;
import com.example.dosa_store.UserActivity;
import com.example.dosa_store.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> userList;
    private UserActivity userActivity;
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    public UserAdapter(List<User> userList, UserActivity userActivity) {
        this.userList = userList;
        this.userActivity = userActivity;
    }
    public interface OnUserClickListener {
        void onUserDeleteClick(User user);
        // You can add more click actions as needed
    }

    private OnUserClickListener clickListener;

    public UserAdapter(List<User> userList, OnUserClickListener clickListener) {
        this.userList = userList;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvId.setText(String.valueOf(user.getId()));
        holder.tvUsername.setText(user.getUesrname());
        holder.tvName.setText(user.getDisplayName());
        holder.tvAddress.setText(user.getAddress());
        holder.tvEmail.setText(user.getEmail());
        holder.tvPhone.setText(user.getPhone());
        holder.deleteButton.setOnClickListener(v -> userActivity.deleteUser(user));

    }
    public void setUserList(List<User> userList){
        this.userList = userList;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvUsername, tvName, tvAddress, tvEmail, tvPhone;
        ImageView deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            deleteButton = itemView.findViewById(R.id.ivDelete);
        }
    }
}