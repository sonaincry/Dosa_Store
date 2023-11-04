package com.example.dosa_store.model;

import java.sql.Date;
import java.util.ArrayList;
import java.time.LocalDate;


public class CreateOrderRequest {
    private int userId;
    private int total;
    private String address;
    private String phone;
    private Date orderDate;
    private String note;
    private String name;
    private ArrayList<OrderDetailDTO> details;

    public CreateOrderRequest(int userId, int total, String address, String phone, Date orderDate, String note, String name, ArrayList<OrderDetailDTO> details) {
        this.userId = userId;
        this.total = total;
        this.address = address;
        this.phone = phone;
        this.orderDate = orderDate;
        this.note = note;
        this.name = name;
        this.details = details;
    }

    public static class OrderDetailDTO {
        private int productId;
        private int quantity;

        public OrderDetailDTO(int productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<OrderDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<OrderDetailDTO> details) {
        this.details = details;
    }
}
