package com.example.dosa_store;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dosa_store.adapter.OrderAdapter;
import com.example.dosa_store.adapter.UserAdapter;
import com.example.dosa_store.model.OrderDto;
import com.example.dosa_store.model.User;
import com.example.dosa_store.repository.OrderRepository;
import com.example.dosa_store.repository.UserRepository;
import com.example.dosa_store.service.OrderService;


import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView rvOrder;
    private OrderAdapter orderAdapter;
    private ArrayList<OrderDto> orderList = new ArrayList<>();
    OrderService orderService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        SSLHelper.handleSSLValidation();
        rvOrder = findViewById(R.id.orderRv);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderList);
        rvOrder.setAdapter(orderAdapter);
        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();
        orderService = OrderRepository.getOrderService(client);
        int id= getIntent().getIntExtra("userId",0);
        Call<OrderDto[]> call = orderService.getByUserId(id);
        call.enqueue(new Callback<OrderDto[]>() {
            @Override
            public void onResponse(Call<OrderDto[]> call, Response<OrderDto[]> response) {
                OrderDto[] orders = response.body();
                if (orders != null) {
                    for (OrderDto order : orders) {
                        orderList.add(order);
                    }
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<OrderDto[]> call, Throwable t) {
                Log.e("API Request Error", t.getMessage());
                // You can also display a Toast message with the error
                Toast.makeText(OrderActivity.this, "API Request Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
