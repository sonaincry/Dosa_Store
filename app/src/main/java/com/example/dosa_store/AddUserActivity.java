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

import com.example.dosa_store.model.User;
import com.example.dosa_store.repository.UserRepository;
import com.example.dosa_store.service.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener{

    UserService userService;
    TextView tvLogin;
    EditText etName, etPassword, etPhone, etAddress, etDisplayName, etEmail;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etDisplayName = findViewById(R.id.etDisplayName);
        etEmail = findViewById(R.id.etEmail);
//        tvLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AddUserActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();
        userService = UserRepository.getUserService(client);
    }
    private void save(){
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();
        String displayName = etDisplayName.getText().toString();
        String email = etEmail.getText().toString();
        User user = new User(name, password, phone, address, displayName, email);
        try{
            Call<User> call = userService.createUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.body() != null){
                        Toast.makeText(AddUserActivity.this, "Save successfully", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(AddUserActivity.this, "save faild", Toast.LENGTH_LONG).show();
                }
            });
        }catch(Exception ex){
            Log.d("Loi", ex.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
            if(v.getId() == R.id.btnRegister){
                save();
            }
        }
}