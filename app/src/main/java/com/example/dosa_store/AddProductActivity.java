package com.example.dosa_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dosa_store.model.Product;
import com.example.dosa_store.model.User;
import com.example.dosa_store.repository.ProductRepository;
import com.example.dosa_store.repository.UserRepository;
import com.example.dosa_store.service.ProductService;
import com.example.dosa_store.service.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener{
    ProductService productService;
    TextView tvBack;
    EditText etName, etPrice, etDescription, etImage, etQuantity;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        etImage = findViewById(R.id.etImage);
        etQuantity = findViewById(R.id.etQuantity);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(this);
        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();

        productService = ProductRepository.getService(client);
    }
    private void save(){
        String name = etName.getText().toString();
        float price = Float.parseFloat(etPrice.getText().toString());
        String description = etDescription.getText().toString();
        String image = etImage.getText().toString();
        int quantity = Integer.parseInt(etQuantity.getText().toString());
        Product product = new Product(name, price, quantity, image, description);
        try{
            Call<Product> call = productService.create(product);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if(response.body() != null){
                        Toast.makeText(AddProductActivity.this, "Save successfully", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Toast.makeText(AddProductActivity.this, "Add successfully", Toast.LENGTH_LONG).show();
                }
            });
        }catch(Exception ex){
                    Log.d("Loi", ex.getMessage());
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAdd){
            save();
        }
    }
}