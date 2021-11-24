package com.example.tp5jr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListadoJuegosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private List<Juegos> listaJuegos;
    private ListadoJuegosAdapter adapter;
    private ListView lstListadoJuegosActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_juegos);

        listaJuegos = new ArrayList<Juegos>();

        int activo = 1;

        obtenerListaApi(activo);

        adapter = new ListadoJuegosAdapter(listaJuegos);

        lstListadoJuegosActivity = findViewById(R.id.lstListadoJuegosActivity);

        lstListadoJuegosActivity.setAdapter(adapter);

        lstListadoJuegosActivity.setOnItemClickListener(this);

        Button btnListadoJuegosActivityCerrarSesion = (Button) findViewById(R.id.btnListadoJuegosActivityCerrarSesion);
        Button btnListadoJuegosActivityAgregar = (Button) findViewById(R.id.btnListadoJuegosActivityAgregar);

        btnListadoJuegosActivityAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoJuegosActivity.this, AgregarJuegoActivity.class);
                startActivity(intent);
            }
        });

        btnListadoJuegosActivityCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoJuegosActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void obtenerListaApi(int activo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.133:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService postService = retrofit.create(ApiService.class);
        Call<List<Juegos>> call = postService.getListadoJuegos(activo);

        call.enqueue(new Callback<List<Juegos>>() {
            @Override
            public void onResponse(Call<List<Juegos>> call, Response<List<Juegos>> response) {
                for(Juegos juegos : response.body()) {
                    listaJuegos.add(juegos);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Juegos>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ListadoJuegosActivity.this, DetalleJuegosActivity.class);
        intent.putExtra("KEY_ID", listaJuegos.get(position).getId_juego());
        startActivity(intent);
    }
}