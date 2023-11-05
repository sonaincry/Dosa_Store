package com.example.dosa_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dosa_store.adapter.UserAdapter;
import com.example.dosa_store.model.User;
import com.example.dosa_store.repository.UserRepository;
import com.example.dosa_store.service.UserService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity{
    private RecyclerView rvUser;
    private UserAdapter userAdapter;
    private ArrayList<User> userList = new ArrayList<>();
    UserService userService;

    Button btnAddUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        SSLHelper.handleSSLValidation();
//        btnAddUser.findViewById(R.id.btnAddUser);
////        btnAddUser.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(UserActivity.this, AddUserActivity.class);
////                startActivity(intent);
////            }
////        });
        rvUser = findViewById(R.id.rvUser);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList);
        rvUser.setAdapter(userAdapter);

        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();
        userService = UserRepository.getUserService(client);

        Call<User[]> call = userService.getAllUsers();
        call.enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                User[] users = response.body();
                if (users != null) {
                    for (User user : users) {
                        userList.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Log.e("API Request Error", t.getMessage());
                // You can also display a Toast message with the error
                Toast.makeText(UserActivity.this, "API Request Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}