package com.example.dosa_store;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import java.util.HashMap;
import java.util.Map;


public class ProductDetailActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        int i=settings.getInt("userId",0);

        int id=getIntent().getIntExtra("id",0);
        String name=getIntent().getStringExtra("name");
        String description=getIntent().getStringExtra("description");
        String image=getIntent().getStringExtra("image");
        float price=getIntent().getFloatExtra("price",0);
        Button add=findViewById(R.id.btnAdd);
        Button go=findViewById(R.id.btnGo);
        ((TextView)findViewById(R.id.item_detail_id)).setText(Integer.toString(id));
        ImageView imageView= findViewById(R.id.item_detail_image);
        Glide.with(ProductDetailActivity.this).load(image).centerCrop().into(imageView);
        ((TextView)findViewById(R.id.item_detail_title)).setText(name);
        ((TextView)findViewById(R.id.item_detail_description)).setText(description);
        ((TextView)findViewById(R.id.item_detail_price)).setText(Float.toString(price));
        add.setOnClickListener(v -> {
            int quantity=Integer.parseInt(((EditText)findViewById(R.id.quantity_input)).getText().toString());
            Gson gson = new Gson();
            String storedHashMapString = settings.getString("cart", null);
            java.lang.reflect.Type type = new TypeToken<HashMap<Integer, Integer>>(){}.getType();
            HashMap<Integer, Integer> cart = gson.fromJson(storedHashMapString, type);
            if(cart==null){
                cart = new HashMap<>();
            }
            if(!cart.containsKey(id)){
                cart.put(id,quantity);
            }else {
                int newValue = cart.get(id) + quantity;
                cart.put(id, newValue);
            }
            String hashMapString = gson.toJson(cart);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("cart",hashMapString );
            editor.apply();
            Toast.makeText(this, cart.get(id)+" "+name+" in your cart !".toString(), Toast.LENGTH_SHORT).show();

        });
        go.setOnClickListener(v -> {
            Intent intent=new Intent(ProductDetailActivity.this,CartActivity.class);

            startActivity(intent);
        });

    }



}