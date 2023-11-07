package com.example.dosa_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dosa_store.model.User;
import com.example.dosa_store.repository.UserRepository;
import com.example.dosa_store.service.UserService;

import org.w3c.dom.Text;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInformationActivity extends AppCompatActivity {

    TextView txtDisplayName, txtLogout, txtOrdHis;
    EditText edtEmail, edtAddress, edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        txtDisplayName = findViewById(R.id.txtDisplayName);
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        txtLogout = findViewById(R.id.txtLogOut);
        txtOrdHis = findViewById(R.id.txtOrdHis);
        // Retrieve the userId from the Intent
        int userId = getIntent().getIntExtra("userId", -1);

        if (userId != -1) {

            OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();
            UserService userService = UserRepository.getUserService(client); // Replace 'client' with your OkHttpClient instance
            Call<User> call = userService.getAllUsers(userId);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User user = response.body();

                        txtDisplayName.setText(user.getDisplayName());
                        edtEmail.setText(user.getEmail());
                        edtAddress.setText(user.getAddress());
                        edtPhone.setText(user.getPhone());
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
            txtLogout.setOnClickListener(v ->{
                Intent intent=new Intent(UserInformationActivity.this,LoginActivity.class);
                startActivity(intent);
            });
            txtOrdHis.setOnClickListener(v ->{
                Intent intent=new Intent(UserInformationActivity.this,OrderActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            });
        }
    }
}
