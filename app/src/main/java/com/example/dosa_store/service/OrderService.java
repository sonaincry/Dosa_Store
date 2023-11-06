package com.example.dosa_store.service;

import com.example.dosa_store.model.CreateOrderRequest;
import com.example.dosa_store.model.OrderDto;
import com.example.dosa_store.model.OrderResponse;
import com.example.dosa_store.model.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    @POST("Order")
    Call<OrderResponse> create(@Body CreateOrderRequest model);
    @GET("Order/{id}")
    Call<OrderDto[]> getByUserId(@Path("id") Object id);
}
