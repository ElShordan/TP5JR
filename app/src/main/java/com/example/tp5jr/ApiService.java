package com.example.tp5jr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/login")
    Call<List<Login>> getLogin();

    @GET("/login/{id}")
    Call<Login> getLoginById(@Path("id") int loginId);

    @GET("/listadojuegos")
    Call<List<ListadoJuegos>> getListadoJuegos();

    @GET("/listadojuegos/{id}")
    Call<ListadoJuegos> getListadoById(@Path("id") int listadoId);

    @GET("/detallejuegos")
    Call<List<DetalleJuegos>> getDetalleJuegos();

    @GET("/detallejuegos/{id}")
    Call<DetalleJuegos> getDetalleById(@Path("id") int detalleId);
}
