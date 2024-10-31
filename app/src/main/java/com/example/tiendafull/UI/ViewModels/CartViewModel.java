package com.example.tiendafull.UI.ViewModels;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiendafull.UI.Activities.AuthActivity;
import com.example.tiendafull.UI.Models.AddProductRequest;
import com.example.tiendafull.UI.Models.Cart;
import com.example.tiendafull.UI.Models.CartDetail;
import com.example.tiendafull.UI.Models.DeleteProductResponse;
import com.example.tiendafull.UI.Models.Item;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.Repository.CartRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Cart> cartLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> sessionExpiredLiveData = new MutableLiveData<>();
    private CartRepository cartRepository;
    private SessionManager sessionManager;

    public CartViewModel() {
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.cartRepository = new CartRepository(sessionManager);
    }

    public LiveData<Cart> getCartLiveData() {
        return cartLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getSessionExpiredLiveData() {
        return sessionExpiredLiveData;
    }



    public void getCart() {
        if (sessionManager.isTokenExpired()) {
            errorLiveData.setValue("Sesión expirada");
            sessionExpiredLiveData.setValue(true);
            return;
        }


        cartRepository.getCart().enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    Log.i("MENSAJE", "onResponse: " + response.body().toString());
                    cartLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Error al obtener el carrito");
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                errorLiveData.setValue("Error de red" + t);
            }
        });
    }

    public void addProductToCart(int id_producto, int cantidad) {
        if (sessionManager.isTokenExpired()) {
            errorLiveData.setValue("Sesión expirada");
            sessionExpiredLiveData.setValue(true);
            return;
        }

        cartRepository.addProductToCart(id_producto, cantidad).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    getCart();

                } else {
                    errorLiveData.setValue("Error al agregar producto");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                errorLiveData.setValue("Error de red");
            }
        });
    }


    public void removeProductFromCart(int productId) {
        if (sessionManager.isTokenExpired()) {
            errorLiveData.setValue("Sesión expirada");
            sessionExpiredLiveData.setValue(true);

            return;
        }

        cartRepository.getCart().enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Cart cart = response.body();


                    boolean itemFound = false;

                    for (CartDetail item : cart.getItems()) {

                        if (item.getProducto().getId() == productId) {
                            itemFound = true;
                            int itemId = item.getId();



                            cartRepository.removeProductFromCart(itemId).enqueue(new Callback<DeleteProductResponse>() {
                                @Override
                                public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                                    if (response.isSuccessful()) {
                                        Log.i("MENSAJE", "onResponse: " + response.body().toString());
                                        getCart();
                                    } else {

                                        errorLiveData.setValue("Error al quitar producto");
                                    }
                                }

                                @Override
                                public void onFailure(Call<DeleteProductResponse> call, Throwable t) {

                                    errorLiveData.setValue("Error en la eliminación: " + t.getMessage());
                                }
                            });
                            break;
                        }
                    }


                    if (!itemFound) {
                        errorLiveData.setValue("Producto no encontrado en el carrito");
                    }
                } else {

                    errorLiveData.setValue("Error al obtener el carrito");
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

                errorLiveData.setValue("Error de red: " + t.getMessage());
            }
        });
    }

    public int getCartTotal(){
        Cart actual = cartLiveData.getValue();
        if (actual != null && actual.getItems()!= null) {
        int total= 0;
        for (CartDetail item : actual.getItems()){
            total+= item.getProducto().getPrecio()* item.getCantidad();
        }
        return total;}
        return 0;



    }

    public void deleteCart() {
        if (sessionManager.isTokenExpired()) {
            errorLiveData.setValue("Sesión expirada");
            sessionExpiredLiveData.setValue(true);
            return;
        }

        cartRepository.deleteCart().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Actualizar la cantidad de productos en el carrito a cero
                    sessionManager.setCartProductCount(0);

                    // Notificar al observador para actualizar la interfaz de usuario
                    getCart(); // Esto actualizará cartLiveData, lo que podría estar observándose en la UI
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                errorLiveData.setValue("Error de red: " + t.getMessage());
            }
        });
    }

}