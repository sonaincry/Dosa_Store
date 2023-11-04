package com.example.dosa_store.repository;

import com.example.dosa_store.api.APIClient;
import com.example.dosa_store.service.LoginService;
import com.example.dosa_store.service.OrderService;

import okhttp3.OkHttpClient;

public class OrderRepository {
    public static OrderService getOrderService(OkHttpClient client){
        return APIClient.getClient(client).create(OrderService.class);
    }
}
