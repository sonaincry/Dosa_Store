package com.example.dosa_store.service;

import com.example.dosa_store.model.Login;
import com.example.dosa_store.model.LoginResponse;
import com.example.dosa_store.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {
    @GET("User/login")
    Call<LoginResponse> login(@Query("username") String username,@Query("password") String password );
}
