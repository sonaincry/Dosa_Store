package com.example.dosa_store;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dosa_store.adapter.CartAdapter;
import com.example.dosa_store.model.CartItem;
import com.example.dosa_store.model.CreateOrderRequest;
import com.example.dosa_store.model.OrderResponse;
import com.example.dosa_store.model.Product;
import com.example.dosa_store.model.User;
import com.example.dosa_store.repository.OrderRepository;
import com.example.dosa_store.repository.ProductRepository;
import com.example.dosa_store.repository.UserRepository;
import com.example.dosa_store.service.OrderService;
import com.example.dosa_store.service.ProductService;
import com.example.dosa_store.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);
        SSLHelper.handleSSLValidation();
        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        int i = settings.getInt("userId", 0);



        ProductService productService = ProductRepository.getService(client);
        Call<ArrayList<Product>> call1 = productService.getAll();

        call1.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                Gson gson = new Gson();
                String storedHashMapString = settings.getString("cart", null);
                java.lang.reflect.Type type = new TypeToken<HashMap<Integer, Integer>>() {
                }.getType();
                HashMap<Integer, Integer> cart = gson.fromJson(storedHashMapString, type);
                ArrayList<Product> models = new ArrayList<>();
                Set<Integer> keys = new HashSet<>();
                ArrayList<CartItem> cartItems = new ArrayList<>();
                float total = 0;
                if (cart != null) {
                    keys = cart.keySet();
                }
                models = response.body();
                for (Product p : models) {
                    for (Integer key : keys) {
                        if (p.getId() == key) {
                            cartItems.add(new CartItem(key, p.getName(), p.getPrice(), cart.get(key)));
                            total += cart.get(key) * p.getPrice();
                        }
                    }
                }
                TextView textViewTotal = findViewById(R.id.total);
                textViewTotal.setText(Float.toString(total));
                ListView lv = findViewById(R.id.cartLv);
                CartAdapter adapter = new CartAdapter(cartItems);
                lv.setAdapter(adapter);
                EditText edtnote = findViewById(R.id.editTextNote);
                Button order = (Button) findViewById(R.id.btnOrder);

                float finalTotal = total;
                order.setOnClickListener(v -> {
                    String note = edtnote.getText().toString();

                    UserService userService = UserRepository.getUserService(client);
                    Call<User> call1 = userService.getAllUsers(i);

                    call1.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            User user = response.body();

                                OrderService orderService = OrderRepository.getOrderService(client);

                                ArrayList<CreateOrderRequest.OrderDetailDTO> details = new ArrayList<CreateOrderRequest.OrderDetailDTO>() {
                                };
                                for (CartItem item : cartItems) {
                                    details.add(new CreateOrderRequest.OrderDetailDTO(item.getId(), item.getQuantity()));
                                }
                                Call<OrderResponse> call2 = orderService.create(new CreateOrderRequest(user.getId(), (int) finalTotal, user.getAddress(), user.getPhone(), null, note, user.getUsername(), details));
                                call2.enqueue(new Callback<OrderResponse>() {
                                    @Override
                                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                        if (response.isSuccessful() && response.body() != null) {

                                            Toast.makeText(CartActivity.this, "Order Successfully !", Toast.LENGTH_SHORT).show();
                                            SharedPreferences.Editor editor = settings.edit();
                                            editor.remove("cart");
                                            editor.apply();
                                            final Handler handler = new Handler();
                                            handler.postDelayed(() -> {
                                                Intent intent=new Intent(CartActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }, 2000); // Delay in milliseconds
                                        } else {
                                            Toast.makeText(CartActivity.this,  response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                                        Toast.makeText(CartActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(CartActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

}

