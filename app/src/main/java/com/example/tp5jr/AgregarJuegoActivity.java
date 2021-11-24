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

public class AgregarJuegoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAgregarJuegoActivityAgregar, btnAgregarJuegoActivityVolver,
            btnAgregarJuegoActivityCerrarSesion;
    private EditText edtTxtAgregarJuegoActivityImagen, edtTxtAgregarJuegoActivityNombre,
            edtTxtAgregarJuegoActivityDescripcion, edtTxtAgregarJuegoActivityJugadores,
            edtTxtAgregarJuegoActivityCompatibilidad, edtTxtAgregarJuegoActivityGenero,
            edtTxtAgregarJuegoActivityIdioma, edtTxtAgregarJuegoActivityClasificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_juego);

        this.findViews();
    }

    private void findViews() {
        btnAgregarJuegoActivityAgregar = findViewById(R.id.btnAgregarJuegoActivityAgregar);
        btnAgregarJuegoActivityVolver = findViewById(R.id.btnAgregarJuegoActivityVolver);
        btnAgregarJuegoActivityCerrarSesion = findViewById(R.id.btnAgregarJuegoActivityCerrarSesion);

        edtTxtAgregarJuegoActivityImagen = findViewById(R.id.edtTxtAgregarJuegoActivityImagen);
        edtTxtAgregarJuesgoActivityNombre = findViewById(R.id.edtTxtAgregarJuegoActivityNombre);
        edtTxtAgregarJuegoActivityDescripcion = findViewById(R.id.edtTxtAgregarJuegoActivityDescripcion);
        edtTxtAgregarJuegoActivityJugadores = findViewById(R.id.edtTxtAgregarJuegoActivityJugadores);
        edtTxtAgregarJuegoActivityCompatibilidad = findViewById(R.id.edtTxtAgregarJuegoActivityCompatibilidad);
        edtTxtAgregarJuegoActivityGenero = findViewById(R.id.edtTxtAgregarJuegoActivityGenero);
        edtTxtAgregarJuegoActivityIdioma = findViewById(R.id.edtTxtAgregarJuegoActivityIdioma);
        edtTxtAgregarJuegoActivityClasificacion = findViewById(R.id.edtTxtAgregarJuegoActivityClasificacion);

        btnAgregarJuegoActivityAgregar.setOnClickListener(this);
        btnAgregarJuegoActivityVolver.setOnClickListener(this);
        btnAgregarJuegoActivityCerrarSesion.setOnClickListener(this);
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

            // asignamos juego a agregar
            Juegos juegos = new Juegos();
            juegos.setImagen(edtTxtAgregarJuegoActivityImagen.getText().toString());
            juegos.setNombre(edtTxtAgregarJuegoActivityNombre.getText().toString());
            juegos.setDescripcion(edtTxtAgregarJuegoActivityDescripcion.getText().toString());
            juegos.setJugadores(edtTxtAgregarJuegoActivityJugadores.getText().toString());
            juegos.setCompatibilidad(edtTxtAgregarJuegoActivityCompatibilidad.getText().toString());
            juegos.setGenero(edtTxtAgregarJuegoActivityGenero.getText().toString());
            juegos.setIdioma(edtTxtAgregarJuegoActivityIdioma.getText().toString());
            juegos.setClasificacion(edtTxtAgregarJuegoActivityClasificacion.getText().toString());
            juegos.setActivo(1);

            // estamos agregando nuevo registro
            call = postService.agregarJuegos(juegos);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Toast.makeText(AgregarJuegoActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AgregarJuegoActivity.this, ListadoJuegosActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("API", "onFailure: " + t.getMessage());
                    Toast.makeText(AgregarJuegoActivity.this, "Error, reintente", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validarDatos() {
        if(edtTxtAgregarJuegoActivityImagen.getText().toString().isEmpty()) {
            edtTxtAgregarJuegoActivityImagen.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtAgregarJuegoActivityNombre.getText().toString().isEmpty()) {
            edtTxtAgregarJuegoActivityNombre.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtAgregarJuegoActivityDescripcion.getText().toString().isEmpty()) {
            edtTxtAgregarJuegoActivityDescripcion.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtAgregarJuegoActivityJugadores.getText().toString().isEmpty()) {
            edtTxtAgregarJuegoActivityJugadores.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtAgregarJuegoActivityCompatibilidad.getText().toString().isEmpty()) {
            edtTxtAgregarJuegoActivityCompatibilidad.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtAgregarJuegoActivityGenero.getText().toString().isEmpty()) {
            edtTxtAgregarJuegoActivityGenero.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtAgregarJuegoActivityIdioma.getText().toString().isEmpty()) {
            edtTxtAgregarJuegoActivityIdioma.setError("Este dato es obligatorio.");
            return false;
        }

        if(edtTxtAgregarJuegoActivityClasificacion.getText().toString().isEmpty()) {
            edtTxtAgregarJuegoActivityClasificacion.setError("Este dato es obligatorio.");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnAgregarJuegoActivityAgregar:
                this.guardarDatos();
                break;
            case R.id.btnAgregarJuegoActivityVolver:
                intent = new Intent(AgregarJuegoActivity.this, ListadoJuegosActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAgregarJuegoActivityCerrarSesion:
                intent = new Intent(AgregarJuegoActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}