package com.example.tiendafull.UI.Api;

import com.example.tiendafull.UI.Models.MercadoPagoPreferenceResponse;
import com.example.tiendafull.UI.Models.PreferenceRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MercadoPagoService {

    @POST("create_preference/")
    Call<MercadoPagoPreferenceResponse> createPreference(@Body PreferenceRequest preferenceRequest);
}
