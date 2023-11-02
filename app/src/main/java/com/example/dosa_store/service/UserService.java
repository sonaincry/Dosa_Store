package com.example.dosa_store.service;

import com.example.dosa_store.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    String USERS = "User";
    @GET(USERS)
    Call<User[]> getAllUsers();
    @GET(USERS + "(/id)")
    Call<User> getAllUsers(@Path("id") Object id);
    @POST(USERS)
    Call<User> createUser(@Body User user);
    @PUT(USERS + "(/id)")
    Call<User> updateUser(@Path("id") Object id, @Body User user);
    @DELETE(USERS + "(/id)")
    Call<User> deleteUser(@Path("id") Object id);
}
