package com.aquino.widgetapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    String API_BASE_URL = "https://eventos-stefanny09.c9users.io";

    @GET("api/v1/eventos")
    Call<List<Evento>> getEventos();

}
