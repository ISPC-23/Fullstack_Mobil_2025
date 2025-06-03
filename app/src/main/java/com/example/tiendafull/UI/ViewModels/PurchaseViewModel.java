package com.example.tiendafull.UI.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiendafull.UI.Models.CancelPurchaseResponse;
import com.example.tiendafull.UI.Models.Purchase;
import com.example.tiendafull.UI.Models.PurchaseConfirmResponse;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.Repository.PurchaseRepository;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseViewModel extends ViewModel {
    private PurchaseRepository purchaseRepository;
    private SessionManager sessionManager;
    private MutableLiveData<PurchaseConfirmResponse> purchaseConfirmResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> purchaseErrorLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Purchase>> purchaseListLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> sessionExpiredLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> canceladaLiveData = new MutableLiveData<>();
    private MutableLiveData<String> initPointLiveData = new MutableLiveData<>();

    public PurchaseViewModel() {
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.purchaseRepository = new PurchaseRepository(sessionManager);
    }

    public LiveData<String> getInitPointLiveData() {
        return initPointLiveData;
    }

    public LiveData<PurchaseConfirmResponse> getPurchaseLiveData() {
        return purchaseConfirmResponseMutableLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return purchaseErrorLiveData;
    }

    public LiveData<List<Purchase>> getPurchaseListLiveData() {
        return purchaseListLiveData;
    }

    public LiveData<Boolean> getSessionExpiredLiveData() {
        return sessionExpiredLiveData;
    }

    public LiveData<Boolean> getCancelada() {
        return canceladaLiveData;
    }


    public void confirmPurchase() {
        if (sessionManager.isTokenExpired()) {
            purchaseErrorLiveData.setValue("Sesión expirada");
            sessionExpiredLiveData.setValue(true);
            return;
        }
        purchaseRepository.confirmPurchase().enqueue(new Callback<PurchaseConfirmResponse>() {
            @Override
            public void onResponse(Call<PurchaseConfirmResponse> call, Response<PurchaseConfirmResponse> response) {
                if (response.isSuccessful()) {
                    purchaseConfirmResponseMutableLiveData.setValue(response.body());
                    sessionManager.setLastPurchase(response.body());

                    Log.d("BACKEND_RESPONSE", "Respuesta del servidor: " + new Gson().toJson(response.body()));

                } else {
                    purchaseErrorLiveData.setValue("Error");
                }
            }

            @Override
            public void onFailure(Call<PurchaseConfirmResponse> call, Throwable t) {
                purchaseErrorLiveData.setValue("Error" + t);
            }
        });
    }

    public void fetchUserPurchases() {
        if (sessionManager.isTokenExpired()) {
            purchaseErrorLiveData.setValue("Sesión expirada");
            sessionExpiredLiveData.setValue(true);
            return;
        }

        purchaseRepository.getUserPurchases().enqueue(new Callback<List<Purchase>>() {
            @Override
            public void onResponse(Call<List<Purchase>> call, Response<List<Purchase>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    purchaseListLiveData.setValue(response.body());
                } else {
                    purchaseErrorLiveData.setValue("Failed to fetch user purchases.");
                }
            }

            @Override
            public void onFailure(Call<List<Purchase>> call, Throwable t) {
                purchaseErrorLiveData.setValue(t.getMessage());
            }
        });
    }

    public void cancelPurchase(String id) {
        if (sessionManager.isTokenExpired()) {
            purchaseErrorLiveData.setValue("Sesión expirada");
            sessionExpiredLiveData.setValue(true);
            return;
        }
        purchaseRepository.cancelPurchase(id).enqueue(new Callback<CancelPurchaseResponse>() {
            @Override
            public void onResponse(Call<CancelPurchaseResponse> call, Response<CancelPurchaseResponse> response) {
                if (response.isSuccessful()) {
                    canceladaLiveData.setValue(true);
                    fetchUserPurchases();

                } else {
                    purchaseErrorLiveData.setValue("Error al cancelar la compra: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CancelPurchaseResponse> call, Throwable t) {
                purchaseErrorLiveData.setValue(t.getMessage());
            }
        });
    }
}