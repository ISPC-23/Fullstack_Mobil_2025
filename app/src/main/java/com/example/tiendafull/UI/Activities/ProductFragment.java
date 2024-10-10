package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Adapter.AdaptadorProducto;
import com.example.tiendafull.UI.Models.Products;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment implements AdaptadorProducto.OnProductClickListener {
    private ProductViewModel productViewModel;
    private AdaptadorProducto productAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Products> listaproducts = new ArrayList<>();



    public ProductFragment() {
        //
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rv2);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        SessionManager sessionManager = SessionManager.getInstance(getContext());
        productViewModel.setSessionManager(sessionManager);

        productAdapter = new AdaptadorProducto(this.listaproducts, getContext(), this);
        recyclerView.setAdapter(productAdapter);

        productViewModel.fetchAllProducts();

        productViewModel.getProductListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                if (products != null) {
                    productAdapter.updateProductList(products);
                }
            }
        });
    }

    @Override
    public void onProductClick(String productId) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString("productId", productId);
        productDetailFragment.setArguments(args);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame2, productDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}