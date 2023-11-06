package com.example.dosa_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dosa_store.adapter.ProductManagementAdapter;
import com.example.dosa_store.adapter.UserAdapter;
import com.example.dosa_store.model.Product;
import com.example.dosa_store.model.User;
import com.example.dosa_store.repository.ProductRepository;
import com.example.dosa_store.repository.UserRepository;
import com.example.dosa_store.service.ProductService;

import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductManagement extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView rvProduct;
    private ProductManagementAdapter adapter;
    private ArrayList<Product> productList = new ArrayList<>();

    ProductService productService;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);

        SSLHelper.handleSSLValidation();
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        rvProduct = findViewById(R.id.rvProduct);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductManagementAdapter(productList, this);
        rvProduct.setAdapter(adapter);

        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();
        productService = ProductRepository.getService(client);

        Call<ArrayList<Product>> call = productService.getAll();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product> products = response.body();
                if (products != null) {
                    for (Product product : products) {
                        productList.add(product);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.e("API Request Error", t.getMessage());
                // You can also display a Toast message with the error
                Toast.makeText(ProductManagement.this, "API Request Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delete(int position) {
        try {
            Product product = productList.get(position);
            Call<Product> call = productService.delete(product.getId());

            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        productList.remove(position);
                        adapter.notifyItemRemoved(position);
                    } else {
                        // Handle the delete request error, if needed
                        Log.e("Delete Error", "Failed to delete the product.");
                        Toast.makeText(ProductManagement.this, "Can't delete the product because it relate to another order", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    // Handle the delete request failure, if needed
                    Log.e("Delete Successfully!", t.getMessage());
                    Toast.makeText(ProductManagement.this, "Delete Request Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception ex) {
            ex.printStackTrace(); // Log any exceptions
            Toast.makeText(this, "An error occurred while deleting the product", Toast.LENGTH_SHORT).show();
        }
    }
    public void update(int position, Product product) {
        try {
            Product currenProduct = productList.get(position);
            Intent intent = new Intent(ProductManagement.this, UpdateProductActivity.class);
            intent.putExtra("id", currenProduct.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "An error occurred while updating the product", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAdd){
            Intent intent = new Intent(ProductManagement.this, AddProductActivity.class);
            startActivity(intent);
        }
    }
}