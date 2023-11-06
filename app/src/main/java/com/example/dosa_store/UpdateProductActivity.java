package com.example.dosa_store;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dosa_store.model.Product;
import com.example.dosa_store.repository.ProductRepository;
import com.example.dosa_store.service.ProductService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProductActivity extends AppCompatActivity {
    EditText etName, etPrice, etQuantity, etImage, etDescription;
    Button btnUpdate;
    ProductService productService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        
    }
}

