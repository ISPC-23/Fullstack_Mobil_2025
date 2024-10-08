package com.example.tiendafull.UI.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiendafull.R;

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

        productViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product != null) {
                    productName.setText(product.getModel());
                    productDescription.setText(product.getDetail());
                    productPrice.setText("Precio: " + product.getPrice());
                    Glide.with(requireContext())
                            .load(product.getImage())
                            .into(productImage);
                }
            }
        });

        return view;
    }
}