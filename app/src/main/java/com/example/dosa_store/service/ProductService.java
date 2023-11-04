package com.example.dosa_store.service;

import com.example.dosa_store.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {
    @GET("Products")
    Call<ArrayList<Product>> getAll();
    @GET("Products/{id}")
    Call<Product> getById(@Path("id") Object id);
    @POST("Products")
    Call<Product> create( @Body Product model);
    @PUT("Products/{id}")
    Call<Product> update(@Path("id") Object id, @Body Product model);
    @DELETE("Products/{id}")
    Call<Product> delete(@Path("id") Object id);
}
