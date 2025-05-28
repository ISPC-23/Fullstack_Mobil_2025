package com.example.tiendafull.UI.Repository;

import com.example.tiendafull.UI.Api.MercadoPagoService;
import com.example.tiendafull.UI.Api.RetrofitClient;
import com.example.tiendafull.UI.Models.MercadoPagoPreferenceResponse;
import com.example.tiendafull.UI.Models.PreferenceRequest;
import com.example.tiendafull.UI.Models.SessionManager;

import retrofit2.Call;

public class MercadoPagoRepository {

   private MercadoPagoService mercadoPagoService;

   public MercadoPagoRepository(SessionManager sessionManager){
       mercadoPagoService= RetrofitClient.getRetrofit(sessionManager).create(MercadoPagoService.class);
   }
    public Call<MercadoPagoPreferenceResponse> createPreference(PreferenceRequest preferenceRequest) {
        return mercadoPagoService.createPreference(preferenceRequest);
    }
}
