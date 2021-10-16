package com.example.tp5jr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText edtTxtLoginActivityUsername, edtTxtLoginActivityContrasenia;
    private Button btnLoginActivityIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // comprobarUsuario(id);
    }

    private void comprobarUsuario(int id) {
        edtTxtLoginActivityUsername = findViewById(R.id.edtTxtLoginActivityUsername);
        edtTxtLoginActivityContrasenia = findViewById(R.id.edtTxtLoginActivityContrasenia);
        btnLoginActivityIngresar = findViewById(R.id.btnLoginActivityIngresar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService postService = retrofit.create(ApiService.class);
        Call<Login> call = postService.getLoginById(id);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                btnLoginActivityIngresar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Login login = (Login) response.body();

                        String username = edtTxtLoginActivityUsername.getText().toString();
                        String contrasenia = edtTxtLoginActivityContrasenia.getText().toString();

                        if(username.equals(login.getUsuario()) && contrasenia.equals(login.getClave())) {
                            saveLoginSharedPreferences(username);

                            Intent intent = new Intent(LoginActivity.this, ListadoJuegosActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
            }
        });
    }

    private void saveLoginSharedPreferences(String username) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.apply();
    }
}