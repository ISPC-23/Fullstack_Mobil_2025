package com.example.tiendafull.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiendafull.UI.Models.Products;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.Repository.ProductRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private SessionManager sessionManager;
    private MutableLiveData<List<Products>> productListLiveData = new MutableLiveData<>();
    private MutableLiveData<Products> productLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public ProductViewModel() {

    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.productRepository= new ProductRepository(sessionManager);
    }

    public LiveData<List<Products>> getProductListLiveData() {
        return productListLiveData;
    }

    public LiveData<Products> getProductLiveData() {
        return productLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchAllProducts() {
        productRepository.getAllProducts().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> productList = response.body();

                    if (productList != null) {

                        productListLiveData.postValue(productList);
                    }
                } else {
                    errorLiveData.postValue("Error fetching products");
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void fetchProductById(String id) {
        productRepository.getProductById(id).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {
                    productLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error fetching product");
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

}

