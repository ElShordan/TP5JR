package com.example.tp5jr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistroActivityGuardar, btnRegistroActivityIngresar;
    private EditText edtTxtRegistroActivityUsername, edtTxtRegistroActivityContrasenia,
                     edtTxtRegistroActivityNombre, edtTxtRegistroActivityApellido,
                     edtTxtRegistroActivityEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.findViews();
    }

    private void findViews() {
        btnRegistroActivityGuardar = findViewById(R.id.btnRegistroActivityGuardar);
        btnRegistroActivityIngresar = findViewById(R.id.btnRegistroActivityIngresar);

        edtTxtRegistroActivityUsername = findViewById(R.id.edtTxtRegistroActivityUsername);
        edtTxtRegistroActivityContrasenia = findViewById(R.id.edtTxtRegistroActivityContrasenia);
        edtTxtRegistroActivityNombre = findViewById(R.id.edtTxtRegistroActivityNombre);
        edtTxtRegistroActivityApellido = findViewById(R.id.edtTxtRegistroActivityApellido);
        edtTxtRegistroActivityEmail = findViewById(R.id.edtTxtRegistroActivityEmail);

        btnRegistroActivityGuardar.setOnClickListener(this);
        btnRegistroActivityIngresar.setOnClickListener(this);
    }

    private void guardarDatos() {
        if(this.validarDatos()) {
            // definimos llamada al API
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.5.133:5000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiService postService = retrofit.create(ApiService.class);
            Call<Boolean> call = null;

            // asignamos usuario a agregar
            Usuarios usuarios = new Usuarios();
            usuarios.setUsuario(edtTxtRegistroActivityUsername.getText().toString());
            usuarios.setClave(edtTxtRegistroActivityContrasenia.getText().toString());
            usuarios.setNombre(edtTxtRegistroActivityNombre.getText().toString());
            usuarios.setApellido(edtTxtRegistroActivityApellido.getText().toString());
            usuarios.setEmail(edtTxtRegistroActivityEmail.getText().toString());

            // estamos agregando nuevo registro
            call = postService.agregarUsuarios(usuarios);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Toast.makeText(RegistroActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("API", "onFailure: " + t.getMessage());
                    Toast.makeText(RegistroActivity.this, "Error, reintente", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validarDatos() {
        if(edtTxtRegistroActivityUsername.getText().toString().isEmpty()) {
            edtTxtRegistroActivityUsername.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtRegistroActivityContrasenia.getText().toString().isEmpty()) {
            edtTxtRegistroActivityContrasenia.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtRegistroActivityNombre.getText().toString().isEmpty()) {
            edtTxtRegistroActivityNombre.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtRegistroActivityApellido.getText().toString().isEmpty()) {
            edtTxtRegistroActivityApellido.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtRegistroActivityEmail.getText().toString().isEmpty()) {
            edtTxtRegistroActivityEmail.setError("Este dato es obligatorio.");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistroActivityGuardar:
                this.guardarDatos();
                break;
            case R.id.btnRegistroActivityIngresar:
                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}