package com.example.dosa_store;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dosa_store.adapter.ProductAdapter;
import com.example.dosa_store.model.Product;
import com.example.dosa_store.repository.ProductRepository;
import com.example.dosa_store.service.ProductService;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        SSLHelper.handleSSLValidation();
        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();
        ProductService productService= ProductRepository.getService(client);
        Call<ArrayList<Product>> call=productService.getAll();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
//
                ArrayList<Product> models= response.body();
                if(models!=null){
                    ProductAdapter adapter=new ProductAdapter(models);
                    ListView lv=findViewById(R.id.lv);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(((parent, view, position, id) -> {
                        Intent intent=new Intent(MainActivity.this, ProductDetailActivity.class);
                        intent.putExtra("id",models.get(position).getId());
                        intent.putExtra("name",models.get(position).getName());
                        intent.putExtra("image",models.get(position).getImgUrl());
                        intent.putExtra("description",models.get(position).getDescription());
                        intent.putExtra("price",models.get(position).getPrice());

                        startActivity(intent);
                    }));
                    Toast.makeText(MainActivity.this,response.message(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        Button goToCart=(Button) findViewById(R.id.btnCart);
        goToCart.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,CartActivity.class);
            startActivity(intent);
        });
    }
}