package com.example.dosa_store.service;

import com.example.dosa_store.model.CreateOrderRequest;
import com.example.dosa_store.model.OrderResponse;
import com.example.dosa_store.model.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {
    @POST("Order")
    Call<OrderResponse> create(@Body CreateOrderRequest model);
}
