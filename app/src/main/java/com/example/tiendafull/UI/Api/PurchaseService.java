package com.example.tiendafull.UI.Api;


import com.example.tiendafull.UI.Models.Purchase;
import com.example.tiendafull.UI.Models.PurchaseConfirmResponse;


import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PurchaseService {
    @POST("purchase/confirm_purchase/")
    Call<PurchaseConfirmResponse> confirmPurchase();

    @GET("purchase/user_purchases")
    Call<List<Purchase>> getUserPurchases();
}
