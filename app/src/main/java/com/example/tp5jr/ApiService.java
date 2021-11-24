package com.example.tp5jr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("usuarios/consultar")
    Call<Boolean> getLogin(@Query("usuario") String usuario, @Query("clave") String clave);

    @POST("usuarios/agregar")
    Call<Boolean> agregarUsuarios(@Body Usuarios usuarios);

    /* @GET("login")
    Call<List<Login>> getLogin();

    @GET("login/{id}")
    Call<Login> getLoginById(@Path("id") int loginId); */

    @GET("juegos/listartodos")
    Call<List<Juegos>> getListadoJuegos(@Query("activo") int activo);

    @GET("juegos/listarporid/{id}")
    Call<Juegos> getListadoById(@Path("id") int listadoId);

    @POST("juegos/agregar")
    Call<Boolean> agregarJuegos(@Body Juegos juegos);

    @PUT("juegos/eliminarporid/{id}")
    Call<Boolean> eliminarJuegoById(@Path("id") int eliminarJuegoId);
}
