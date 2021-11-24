package com.example.tp5jr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleJuegosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_juegos);

        Bundle extras = getIntent().getExtras();
        int detallejuegosId = extras.getInt("KEY_ID");

        Button btnDetalleJuegosActivityEliminar = (Button) findViewById(R.id.btnDetalleJuegosActivityEliminar);
        Button btnDetalleJuegosActivityVolver = (Button) findViewById(R.id.btnDetalleJuegosActivityVolver);
        Button btnDetalleJuegosActivityCerrarSesion = (Button) findViewById(R.id.btnDetalleJuegosActivityCerrarSesion);

        btnDetalleJuegosActivityEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarDatos(detallejuegosId);
            }
        });

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

        cargarDetalleJuegos(detallejuegosId);
    }

    private void cargarDetalleJuegos(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.133:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService postService = retrofit.create(ApiService.class);
        Call<Juegos> call = postService.getListadoById(id);

        call.enqueue(new Callback<Juegos>() {
            @Override
            public void onResponse(Call<Juegos> call, Response<Juegos> response) {
                Juegos detalleJuegos = (Juegos) response.body();

                ImageView imgDetalleJuegosActivityJuego = (ImageView) findViewById(R.id.imgDetalleJuegosActivityJuego);
                TextView txtDetalleJuegosActivityNombre = (TextView) findViewById(R.id.txtDetalleJuegosActivityNombre);
                TextView txtDetalleJuegosActivityDescripcion = (TextView) findViewById(R.id.txtDetalleJuegosActivityDescripcion);
                TextView txtDetalleJuegosActivityJugadores = (TextView) findViewById(R.id.txtDetalleJuegosActivityJugadores);
                TextView txtDetalleJuegosActivityCompatibilidad = (TextView) findViewById(R.id.txtDetalleJuegosActivityCompatibilidad);
                TextView txtDetalleJuegosActivityGenero = (TextView) findViewById(R.id.txtDetalleJuegosActivityGenero);
                TextView txtDetalleJuegosActivityIdioma = (TextView) findViewById(R.id.txtDetalleJuegosActivityIdioma);
                TextView txtDetalleJuegosActivityClasificacion = (TextView) findViewById(R.id.txtDetalleJuegosActivityClasificacion);
                CheckBox chkDetalleJuegosActivityActivo = findViewById(R.id.chkDetalleJuegosActivityActivo);

                Picasso.get()
                        .load(detalleJuegos.getImagen())
                        .into(imgDetalleJuegosActivityJuego);

                txtDetalleJuegosActivityNombre.setText(detalleJuegos.getNombre());
                txtDetalleJuegosActivityDescripcion.setText(detalleJuegos.getDescripcion());
                txtDetalleJuegosActivityJugadores.setText("Jugadores: " + detalleJuegos.getJugadores());
                txtDetalleJuegosActivityCompatibilidad.setText("Compatibilidad: " + detalleJuegos.getCompatibilidad());
                txtDetalleJuegosActivityGenero.setText("Género: " + detalleJuegos.getGenero());
                txtDetalleJuegosActivityIdioma.setText("Idioma: " + detalleJuegos.getIdioma());
                txtDetalleJuegosActivityClasificacion.setText("Clasificación: " + detalleJuegos.getClasificacion());
                chkDetalleJuegosActivityActivo.setChecked(detalleJuegos.getActivo() == 1 ? true : false);
            }

            @Override
            public void onFailure(Call<Juegos> call, Throwable t) {
            }
        });
    }

    private void grabarDatos(int id) {
        // definimos llamada al API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.133:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService postService = retrofit.create(ApiService.class);
        Call<Boolean> call = null;

        CheckBox chkDetalleJuegosActivityActivo = findViewById(R.id.chkDetalleJuegosActivityActivo);

        call = postService.eliminarJuegoById(id);

        if(!chkDetalleJuegosActivityActivo.isChecked()) {
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    // asignamos juego a "eliminar"
                    Juegos juegos = new Juegos();

                    Toast.makeText(DetalleJuegosActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DetalleJuegosActivity.this, ListadoJuegosActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("API", "onFailure: " + t.getMessage());
                    Toast.makeText(DetalleJuegosActivity.this, "Error, reintente", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(DetalleJuegosActivity.this, "No se marcó checkbox", Toast.LENGTH_SHORT).show();
        }
    }
}
