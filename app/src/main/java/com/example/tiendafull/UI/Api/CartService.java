package com.example.tiendafull.UI.Api;

import com.example.tiendafull.UI.Models.AddProductRequest;
import com.example.tiendafull.UI.Models.Cart;
import com.example.tiendafull.UI.Models.DeleteProductRequest;
import com.example.tiendafull.UI.Models.DeleteProductResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface CartService {
    @POST("cart/crear_carrito")
    Call<String> createCart();

    @GET("cart/items")
    Call<Cart> getCart();

    @POST("cart/agregar_producto/")
    Call<String> agregar_producto(@Body AddProductRequest addProductRequest);

    @HTTP(method = "DELETE", path = "cart/delete_item/", hasBody = true)
    Call<DeleteProductResponse> delete_item(@Body DeleteProductRequest deleteProductRequest);






}