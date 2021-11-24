package com.example.tp5jr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTxtLoginActivityUsername, edtTxtLoginActivityContrasenia;
    private Button btnLoginActivityIngresar, btnLoginActivityRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.findViews();
    }

    private void findViews() {
        edtTxtLoginActivityUsername = findViewById(R.id.edtTxtLoginActivityUsername);
        edtTxtLoginActivityContrasenia = findViewById(R.id.edtTxtLoginActivityContrasenia);
        btnLoginActivityIngresar = findViewById(R.id.btnLoginActivityIngresar);
        btnLoginActivityRegistrar = findViewById(R.id.btnLoginActivityRegistrar);

        btnLoginActivityIngresar.setOnClickListener(this);
        btnLoginActivityRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginActivityIngresar:
                if(this.validarDatos()) {
                    String username = edtTxtLoginActivityUsername.getText().toString();
                    String contrasenia = edtTxtLoginActivityContrasenia.getText().toString();

                    this.verificarLoginApi(username, contrasenia);
                }
                break;
            case R.id.btnLoginActivityRegistrar:
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean validarDatos() {
        if(edtTxtLoginActivityUsername.getText().toString().isEmpty()) {
            edtTxtLoginActivityUsername.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtLoginActivityContrasenia.getText().toString().isEmpty()) {
            edtTxtLoginActivityContrasenia.setError("Este dato es obligatorio.");
            return false;
        }
        return true;
    }

    private void verificarLoginApi(String username, String contrasenia) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.133:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService postService = retrofit.create(ApiService.class);
        Call<Boolean> call = postService.getLogin(username, contrasenia);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()) {
                    loginAprobado();
                } else {
                    loginNoAprobado();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("API", "onFailure: error interno de validación de API");
            }
        });
    }

    private void loginAprobado() {
        limpiarControles();

        Intent intent = new Intent(LoginActivity.this, ListadoJuegosActivity.class);
        startActivity(intent);
    }

    private void loginNoAprobado() {
        Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
    }

    private void limpiarControles() {
        edtTxtLoginActivityUsername.setText("");
        edtTxtLoginActivityContrasenia.setText("");
    }
}
