package com.example.tiendafull.UI.Repository;

import com.example.tiendafull.UI.Api.ProductService;
import com.example.tiendafull.UI.Api.RetrofitClient;
import com.example.tiendafull.UI.Models.Products;
import com.example.tiendafull.UI.Models.SessionManager;

import java.util.List;

import retrofit2.Call;

public class ProductRepository {
    private ProductService productService;

    public ProductRepository(SessionManager sessionManager) {
        productService = RetrofitClient.getRetrofit(sessionManager).create(ProductService.class);
    }

    public Call<List<Products>> getAllProducts() {
        return productService.getAllProducts();
    }

    public Call<Products> getProductById(String id) {
        return productService.getProductById(id);
    }

}