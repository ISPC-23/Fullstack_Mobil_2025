package com.example.tiendafull.UI.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.Products;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.ProductViewModel;

public class ProductDetailFragment extends Fragment {
    private ProductViewModel productViewModel;
    private TextView productName, productDescription, productPrice;
    private ImageView productImage;
    private String productId;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString("productId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        productName = view.findViewById(R.id.tvProductName);
        productDescription = view.findViewById(R.id.tvProductDescription);
        productPrice = view.findViewById(R.id.tvProductPrice);
        productImage = view.findViewById(R.id.ivProductImage);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        SessionManager sessionManager = SessionManager.getInstance(getContext());
        productViewModel.setSessionManager(sessionManager);

        if (productId != null) {
            productViewModel.fetchProductById(productId);
        }

        productViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<Products>() {
            @Override
            public void onChanged(Products product) {
                if (product != null) {
                    productName.setText(product.getModelo());
                    productDescription.setText(product.getDetalle());
                    productPrice.setText("$" + product.getPrecio());

                    Glide.with(requireContext())
                            .load(product.getImagen())
                            .into(productImage);
                }
            }
        });

        return view;
    }
}