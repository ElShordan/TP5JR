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
    private List<ListadoJuegos> listaListadoJuegos;
    private ListadoJuegosAdapter adapter;
    private ListView lstListadoJuegosActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_juegos);

        listaListadoJuegos = new ArrayList<ListadoJuegos>();

        obtenerListaApi();

        adapter = new ListadoJuegosAdapter(listaListadoJuegos);

        lstListadoJuegosActivity = findViewById(R.id.lstListadoJuegosActivity);

        lstListadoJuegosActivity.setAdapter(adapter);

        lstListadoJuegosActivity.setOnItemClickListener(this);

        Button btnListadoJuegosActivityCerrarSesion = (Button) findViewById(R.id.btnListadoJuegosActivityCerrarSesion);

        btnListadoJuegosActivityCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoJuegosActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void obtenerListaApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService postService = retrofit.create(ApiService.class);
        Call<List<ListadoJuegos>> call = postService.getListadoJuegos();

        call.enqueue(new Callback<List<ListadoJuegos>>() {
            @Override
            public void onResponse(Call<List<ListadoJuegos>> call, Response<List<ListadoJuegos>> response) {
                for(ListadoJuegos listadoJuegos : response.body()) {
                    listaListadoJuegos.add(listadoJuegos);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListadoJuegos>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ListadoJuegosActivity.this, DetalleJuegosActivity.class);
        intent.putExtra("KEY_ID", listaListadoJuegos.get(position).getId());
        startActivity(intent);
    }
}