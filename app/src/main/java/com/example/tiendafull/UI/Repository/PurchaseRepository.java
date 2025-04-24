package com.example.tiendafull.UI.Repository;


import com.example.tiendafull.UI.Api.PurchaseService;
import com.example.tiendafull.UI.Api.RetrofitClient;

import com.example.tiendafull.UI.Models.CancelPurchaseRequest;
import com.example.tiendafull.UI.Models.CancelPurchaseResponse;
import com.example.tiendafull.UI.Models.Purchase;
import com.example.tiendafull.UI.Models.PurchaseConfirmResponse;
import com.example.tiendafull.UI.Models.SessionManager;

import java.util.List;

import retrofit2.Call;

public class PurchaseRepository {
    private PurchaseService purchaseService;

    public PurchaseRepository(SessionManager sessionManager) {
        purchaseService = RetrofitClient.getRetrofit(sessionManager).create(PurchaseService.class);
    }

    public Call<PurchaseConfirmResponse> confirmPurchase() {
        return purchaseService.confirmPurchase();
    }
    public Call<List<Purchase>> getUserPurchases(){
        return purchaseService.getUserPurchases();
    }
    public Call<CancelPurchaseResponse> cancelPurchase(String id){

        CancelPurchaseRequest cancelPurchaseRequest=new CancelPurchaseRequest(true);
        return purchaseService.cancelPurchase(id, cancelPurchaseRequest);
    }

}


