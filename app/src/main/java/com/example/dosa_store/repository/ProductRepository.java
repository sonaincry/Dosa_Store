package com.example.dosa_store.repository;

import com.example.dosa_store.api.APIClient;
import com.example.dosa_store.service.ProductService;

import okhttp3.OkHttpClient;

public class ProductRepository {
    public static ProductService getService(OkHttpClient client){
        return APIClient.getClient(client).create(ProductService.class);
    }
}
