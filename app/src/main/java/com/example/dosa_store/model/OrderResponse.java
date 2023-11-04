package com.example.dosa_store.model;

public class OrderResponse {
    private int id;
    private String username;

    public OrderResponse(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public OrderResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
