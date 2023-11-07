package com.example.dosa_store;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dosa_store.adapter.ProductRecyclerViewAdapter;
import com.example.dosa_store.model.Product;
import com.example.dosa_store.repository.ProductRepository;
import com.example.dosa_store.service.ProductService;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<Product> models;
    ProductRecyclerViewAdapter adapter;
    RecyclerView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_product);
        setContentView(R.layout.activity_productrv);

        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        int userId = settings.getInt("userId", -1);

        SSLHelper.handleSSLValidation();
        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();
        ProductService productService= ProductRepository.getService(client);
        Call<ArrayList<Product>> call=productService.getAll();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                models= response.body();
                adapter=new ProductRecyclerViewAdapter(models);
                lv=findViewById(R.id.recyclerView);
                lv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                lv.setAdapter(adapter);
                ((Button) findViewById(R.id.btnSearch)).setOnClickListener(v->{
                    EditText search=findViewById(R.id.textSearch);
                    if(search.getText()==null|| search.getText().toString().isEmpty()){
                        models= response.body();
                        adapter=new ProductRecyclerViewAdapter(models);
                        lv.setAdapter(adapter);
                    }else{
                        String searchTxt=search.getText().toString();
                        ArrayList<Product> searchList=new ArrayList<>();
                        if(models==null||models.isEmpty()){
                            models=response.body();
                        }
                        for(Product pro:models){
                            if(pro.getName().toLowerCase().contains(searchTxt.toLowerCase())){
                                searchList.add(pro);
                            }
                        }
                        models=searchList;
                        adapter=new ProductRecyclerViewAdapter(models);
                        lv.setAdapter(adapter);
                    }
                });
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
        TextView tvUserInfo = (TextView) findViewById(R.id.tvUserInfo);
        tvUserInfo.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,UserInformationActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

    }
}