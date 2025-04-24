package com.example.tiendafull.UI.Repository;

import com.example.tiendafull.UI.Api.CartService;
import com.example.tiendafull.UI.Api.RetrofitClient;
import com.example.tiendafull.UI.Models.AddProductRequest;
import com.example.tiendafull.UI.Models.Cart;
import com.example.tiendafull.UI.Models.DeleteProductRequest;
import com.example.tiendafull.UI.Models.DeleteProductResponse;
import com.example.tiendafull.UI.Models.SessionManager;

import retrofit2.Call;

public class CartRepository {
    private CartService cartService;
    private SessionManager sessionManager;

    public CartRepository(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        cartService = RetrofitClient.getRetrofit(sessionManager).create(CartService.class);
    }

    public Call<Cart> getCart() {
        return cartService.getCart();
    }

    public Call<String> addProductToCart(int id_producto, int cantidad) {
        // Llamada al servicio de API para agregar producto
        Call<String> addProductCall = cartService.agregar_producto(new AddProductRequest(id_producto, cantidad));
        // Actualizar el contador de productos en SessionManager
        sessionManager.incrementCartProductCount();
        return addProductCall;
    }

    public Call<DeleteProductResponse> removeProductFromCart(int item_id) {
        // Llamada al servicio de API para eliminar producto
        Call<DeleteProductResponse> removeProductCall = cartService.delete_item(new DeleteProductRequest(item_id));
        // Actualizar el contador de productos en SessionManager
        sessionManager.decrementCartProductCount();
        return removeProductCall;
    }

    public Call<String> createCart() {
        return cartService.createCart();
    }

    public Call<String> deleteCart() {
        return cartService.delete_cart();
    }
}
