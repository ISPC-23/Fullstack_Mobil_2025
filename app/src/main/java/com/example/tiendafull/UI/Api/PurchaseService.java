package com.example.tiendafull.UI.Api;


import com.example.tiendafull.UI.Models.CancelPurchaseRequest;
import com.example.tiendafull.UI.Models.CancelPurchaseResponse;
import com.example.tiendafull.UI.Models.DeleteProductRequest;
import com.example.tiendafull.UI.Models.Purchase;
import com.example.tiendafull.UI.Models.PurchaseConfirmResponse;


import java.util.List;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PurchaseService {
    @POST("purchase/confirm_purchase/")
    Call<PurchaseConfirmResponse> confirmPurchase();

    @GET("purchase/user_purchases")
    Call<List<Purchase>> getUserPurchases();

    @PATCH("purchase/{id}/cancel_purchase/")
    Call<CancelPurchaseResponse> cancelPurchase(@Path("id") String id, @Body CancelPurchaseRequest cancelPurchaseRequest);
}
