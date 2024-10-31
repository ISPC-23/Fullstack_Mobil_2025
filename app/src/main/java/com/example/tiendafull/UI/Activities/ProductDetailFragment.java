package com.example.tiendafull.UI.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.Products;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.CartViewModel;
import com.example.tiendafull.UI.ViewModels.ProductViewModel;

public class ProductDetailFragment extends Fragment {
    private ProductViewModel productViewModel;
    private CartViewModel cartViewModel;
    private TextView productName, productDescription, productPrice;
    private ImageView productImage;
    private String productId;
    private Button agregar, quitar;

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
        agregar = view.findViewById(R.id.agregar);
        quitar = view.findViewById(R.id.quitar);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        SessionManager sessionManager = SessionManager.getInstance(getContext());
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.setSessionManager(sessionManager);
        productViewModel.setSessionManager(sessionManager);

        cartViewModel.getSessionExpiredLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSessionExpired) {
                if (isSessionExpired != null && isSessionExpired) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Sesión expirada")
                            .setMessage("Tu sesión ha expirado. Por favor, inicia sesión nuevamente.")
                            .setCancelable(false)
                            .setPositiveButton("Iniciar sesión", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getContext(), AuthActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.addProductToCart(Integer.parseInt(productId), 1);
                sessionManager.incrementCartProductCount(); // Incrementar el conteo en SessionManager
                Toast.makeText(getActivity(), "Agregado", Toast.LENGTH_SHORT).show();
                updateCartIconColor();
            }
        });

        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.removeProductFromCart(Integer.parseInt(productId));
                sessionManager.decrementCartProductCount(); // Decrementar el conteo en SessionManager
                Toast.makeText(getActivity(), "Eliminado", Toast.LENGTH_SHORT).show();
                Log.i("VER", "p" + productId);
                updateCartIconColor();
            }
        });

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
                    Glide.with(requireContext()).load(product.getImagen()).into(productImage);
                }
            }
        });

        return view;
    }

    // Método para actualizar el color del ícono del carrito en MainActivity
    private void updateCartIconColor() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.updateCartIconColor();
        }
    }
}
