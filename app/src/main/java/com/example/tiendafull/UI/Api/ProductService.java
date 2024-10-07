package com.example.tiendafull.UI.Api;

import com.example.tiendafull.UI.Models.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    // Obtener todos los productos
    @GET("products/")
    Call<List<Products>> getAllProducts();

    // Obtener un producto por ID
    @GET("products/{id}")
    Call<Products> getProductById(@Path("id") String id);


}
