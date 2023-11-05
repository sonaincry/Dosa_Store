package com.example.dosa_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dosa_store.model.Login;
import com.example.dosa_store.model.LoginResponse;
import com.example.dosa_store.model.Product;
import com.example.dosa_store.repository.LoginRepository;
import com.example.dosa_store.repository.ProductRepository;
import com.example.dosa_store.service.LoginService;
import com.example.dosa_store.service.ProductService;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        SSLHelper.handleSSLValidation();

        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();

        Button login=(Button) findViewById(R.id.cirLoginButton);
        login.setOnClickListener(v->{
            String username=((EditText)findViewById(R.id.editTextUserName)).getText().toString();
            String password=((EditText)findViewById(R.id.editTextPassword)).getText().toString();
            LoginService loginService= LoginRepository.getService(client);
            Call<LoginResponse> call=loginService.login(username,password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful()&&response.body()!=null){
                        LoginResponse loginResponse= response.body();
                        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("userId", loginResponse.getId());
                        editor.apply();
                        if(!loginResponse.isRole()){
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "You're not a admin", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, response.message()
                                , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}