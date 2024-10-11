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

    public CartRepository(SessionManager sessionManager) {
        cartService = RetrofitClient.getRetrofit(sessionManager).create(CartService.class);
    }

    public Call<Cart> getCart() {
        return cartService.getCart();
    }

    public Call<String> addProductToCart(int id_producto, int cantidad) {
        return cartService.agregar_producto(new AddProductRequest(id_producto, cantidad));
    }

    public Call<DeleteProductResponse> removeProductFromCart(int item_id) {
        return cartService.delete_item(new DeleteProductRequest(item_id));
    }

    public Call<String> createCart() {
        return cartService.createCart();
    }
}