package com.example.tiendafull.UI.ViewModels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiendafull.UI.Models.AddProductRequest;
import com.example.tiendafull.UI.Models.Cart;
import com.example.tiendafull.UI.Models.CartDetail;
import com.example.tiendafull.UI.Models.DeleteProductResponse;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.Repository.CartRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Cart> cartLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
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

    // Obtener carrito desde el backend
    public void getCart() {


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

    // Agregar producto al carrito
    public void addProductToCart(int id_producto, int cantidad) {


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

    // Quitar producto del carrito
    public void removeProductFromCart(int productId) {
        // Primero, obtener el carrito del usuario
        cartRepository.getCart().enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Cart cart = response.body();

                    // Buscar el item correspondiente al productId
                    boolean itemFound = false; // Para verificar si se encontró el item

                    for (CartDetail item : cart.getItems()) {
                        // Compara el ID del producto con el productId
                        if (item.getProducto().getId() == productId) {
                            itemFound = true; // Marcamos que se encontró el item
                            int itemId = item.getId();


                            // Hacer la solicitud para eliminar el producto
                            cartRepository.removeProductFromCart(itemId).enqueue(new Callback<DeleteProductResponse>() {
                                @Override
                                public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                                    if (response.isSuccessful()) {
                                        Log.i("MENSAJE", "onResponse: " + response.body().toString());
                                        getCart();
                                    } else {
                                        // Manejo del error en la eliminación
                                        errorLiveData.setValue("Error al quitar producto");
                                    }
                                }

                                @Override
                                public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                                    // Manejo del error
                                    errorLiveData.setValue("Error en la eliminación: " + t.getMessage());
                                }
                            });
                            break; // Salimos del bucle una vez que encontramos el producto
                        }
                    }

                    // Si no se encontró el item
                    if (!itemFound) {
                        errorLiveData.setValue("Producto no encontrado en el carrito");
                    }
                } else {
                    // Manejo de errores si la respuesta no fue exitosa
                    errorLiveData.setValue("Error al obtener el carrito");
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                // Manejo del error
                errorLiveData.setValue("Error de red: " + t.getMessage());
            }
        });
    }

    public void clearCart() {

    }
}