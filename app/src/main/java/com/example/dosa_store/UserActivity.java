package com.example.dosa_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dosa_store.adapter.UserAdapter;
import com.example.dosa_store.model.User;
import com.example.dosa_store.repository.UserRepository;
import com.example.dosa_store.service.UserService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    private RecyclerView rvUser;
    private UserAdapter userAdapter;
    private ArrayList<User> userList = new ArrayList<>(); // Create a list of Trainee objects
    UserService userService;
    ImageView ivAddUser;
    public enum DialogType {
        CREATE,
        UPDATE
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        SSLHelper.handleSSLValidation();

        rvUser = findViewById(R.id.rvUser);
        rvUser.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(userList, this);
        rvUser.setAdapter(userAdapter);

        ivAddUser = findViewById(R.id.ivAddUser);
        ivAddUser.setOnClickListener(v -> showDiaLog(DialogType.CREATE, null));
        OkHttpClient client = SSLSocketFactoryHelper.getUnsafeOkHttpClient().build();
        userService = UserRepository.getUserService(client);

        Call<User[]> call = userService.getAllUsers();
        call.enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                User[] users = response.body();
                if (users != null) {
                    for (User user : users) {
                        userList.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Log.e("API Request Error", t.getMessage());
                // You can also display a Toast message with the error
                Toast.makeText(UserActivity.this, "API Request Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
/*    public void showDialog(DialogType dialogType, User currentUser){
        Dialog dialog = new Dialog(UserActivity.this);
        dialog.setContentView(R.layout.dialog_user);

        TextView textViewHeadingUser = dialog.findViewById(R.id.textViewHeadingUser);
        TextView textViewUserName = dialog.findViewById(R.id.textViewUserName);
        EditText editTextName = dialog.findViewById(R.id.editTextName);
        EditText editTextAddress = dialog.findViewById(R.id.editTextAddress);
        EditText editTextEmail = dialog.findViewById(R.id.editTextEmail);
        EditText editTextPhone = dialog.findViewById(R.id.editTextPhone);
        Button buttonSaveUser = dialog.findViewById(R.id.buttonSaveUser);
        TextView cancelUser = dialog.findViewById(R.id.textViewCancelUser);

        if(dialogType == DialogType.UPDATE){
            textViewHeadingUser.setText("Cập nhật User");
            textViewUserName.setText(currentUser.getUesrname());
            editTextName.setText(currentUser.getDisplayName());
            editTextAddress.setText(currentUser.getAddress());
            editTextEmail.setText(currentUser.getEmail());
            editTextPhone.setText(currentUser.getPhone());


        }else {

        }
        cancelUser.setOnClickListener(v -> dialog.dismiss());
        buttonSaveUser.setOnClickListener(v -> {
            try {
                String name = editTextName.getText().toString();
                String address = editTextAddress.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();

                User user = new User(name, address, email, phone);
            }
        });

        }*/

    public void deleteUser(@NonNull User userToDelete){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle("Delete User");
        Log.d("UserActivity", "ID người dùng cần xóa:" + userToDelete.getId());

        builder.setMessage("Bạn có muốn xóa " + userToDelete.getDisplayName() + " ?");

        builder.setPositiveButton("Có", (dialog, which) -> {

            userService.deleteUser(userToDelete.getId()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    userList.remove(userToDelete);
                    userAdapter.setUserList(userList);
                    userAdapter.notifyDataSetChanged();
                    Toast.makeText(UserActivity.this,
                            "User " + userToDelete.getDisplayName() +" đã bị xóa",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(UserActivity.this,
                            "Có lỗi",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
    public void showDiaLog(DialogType dialogType, User currentUser){
        Dialog dialog = new Dialog(UserActivity.this);
        dialog.setContentView(R.layout.dialog_user);
        TextView tvHeadingUser = dialog.findViewById(R.id.textViewHeadingUser);
        EditText etUserName = dialog.findViewById(R.id.editTextUserName);
        EditText etPassword = dialog.findViewById(R.id.editTextPassword);
        EditText etName = dialog.findViewById(R.id.editTextDisplayName);
        Button btnSave = dialog.findViewById(R.id.buttonSaveUser);

        if (dialogType == DialogType.CREATE){
            tvHeadingUser.setText("Tạo mới User");
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userName = etUserName.getText().toString();
                    String password = etPassword.getText().toString();
                    String displayName = etName.getText().toString();

                    // Create a new User object with the input data
                    User newUser = new User(userName, password, displayName);

                    // Call the createUser function to send the user data to the server
                    createUser(newUser);

                    // Close the dialog
                    dialog.dismiss();
                }
            });

        }else{

        }


    }
    public void createUser(User newUser) {
        Call<User> call = userService.createUser(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User createdUser = response.body();
                    userList.add(response.body());
                    userAdapter.setUserList(userList);
                    // Handle the created user data as needed
                    // For example, add it to your local data and update the UI

                    // Notify the adapter that the data has changed
                    userAdapter.notifyDataSetChanged();

                    Toast.makeText(UserActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserActivity.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserActivity.this, "An error occurred while creating user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}