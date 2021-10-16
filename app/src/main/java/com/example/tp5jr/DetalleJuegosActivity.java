package com.example.tp5jr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleJuegosActivity extends AppCompatActivity {

    private Button btnDetalleJuegosActivityVolver, btnDetalleJuegosActivityCerrarSesion;
    private TextView txtDetalleJuegosActivityNombre, txtDetalleJuegosActivityDescripcion,
            txtDetalleJuegosActivityJugadores, txtDetalleJuegosActivityCompatibilidad,
            txtDetalleJuegosActivityGenero, txtDetalleJuegosActivityIdioma,
            txtDetalleJuegosActivityClasificacion;
    private ImageView imgDetalleJuegosActivityJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_juegos);

        Bundle extras = getIntent().getExtras();
        int detallejuegosId = extras.getInt("KEY_ID");

        findViewsById();

        cargarDetalleJuegos(detallejuegosId);

        btnDetalleJuegosActivityVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleJuegosActivity.this, ListadoJuegosActivity.class);
                startActivity(intent);
            }
        });

        btnDetalleJuegosActivityCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleJuegosActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViewsById() {
        ImageView imgDetalleJuegosActivityJuego = (ImageView) findViewById(R.id.imgDetalleJuegosActivityJuego);
        TextView txtDetalleJuegosActivityNombre = (TextView) findViewById(R.id.txtDetalleJuegosActivityNombre);
        TextView txtDetalleJuegosActivityDescripcion = (TextView) findViewById(R.id.txtDetalleJuegosActivityDescripcion);
        TextView txtDetalleJuegosActivityJugadores = (TextView) findViewById(R.id.txtDetalleJuegosActivityJugadores);
        TextView txtDetalleJuegosActivityCompatibilidad = (TextView) findViewById(R.id.txtDetalleJuegosActivityCompatibilidad);
        TextView txtDetalleJuegosActivityGenero = (TextView) findViewById(R.id.txtDetalleJuegosActivityGenero);
        TextView txtDetalleJuegosActivityIdioma = (TextView) findViewById(R.id.txtDetalleJuegosActivityIdioma);
        TextView txtDetalleJuegosActivityClasificacion = (TextView) findViewById(R.id.txtDetalleJuegosActivityClasificacion);
        Button btnDetalleJuegosActivityVolver = (Button) findViewById(R.id.btnDetalleJuegosActivityVolver);
        Button btnDetalleJuegosActivityCerrarSesion = (Button) findViewById(R.id.btnDetalleJuegosActivityCerrarSesion);
    }

    private void cargarDetalleJuegos(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService postService = retrofit.create(ApiService.class);
        Call<DetalleJuegos> call = postService.getDetalleById(id);

        call.enqueue(new Callback<DetalleJuegos>() {
            @Override
            public void onResponse(Call<DetalleJuegos> call, Response<DetalleJuegos> response) {
                DetalleJuegos detalleJuegos = (DetalleJuegos) response.body();
                txtDetalleJuegosActivityNombre.setText(detalleJuegos.getNombre());
                txtDetalleJuegosActivityDescripcion.setText(detalleJuegos.getDescripcion());
                txtDetalleJuegosActivityJugadores.setText(detalleJuegos.getJugadores());
                txtDetalleJuegosActivityCompatibilidad.setText(detalleJuegos.getCompatibilidad());
                txtDetalleJuegosActivityGenero.setText(detalleJuegos.getGenero());
                txtDetalleJuegosActivityIdioma.setText(detalleJuegos.getIdioma());
                txtDetalleJuegosActivityClasificacion.setText(detalleJuegos.getClasificacion());
                imgDetalleJuegosActivityJuego.setImageResource(detalleJuegos.getImagen());
            }

            @Override
            public void onFailure(Call<DetalleJuegos> call, Throwable t) {
            }
        });
    }
}
