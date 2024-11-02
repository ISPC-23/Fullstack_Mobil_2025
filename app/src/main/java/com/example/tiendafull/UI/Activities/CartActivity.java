package com.example.tiendafull.UI.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Adapter.CartAdapter;
import com.example.tiendafull.UI.Models.Cart;
import com.example.tiendafull.UI.Models.CartDetail;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity implements CartAdapter.OnProductCartClickListener {

    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private ArrayList<CartDetail> arrayList = new ArrayList<>();
    private TextView tvTotalPrice;
    private Button checkoutButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_cart);


        checkoutButton = findViewById(R.id.checkoutButton);
        rvCartItems = findViewById(R.id.recyclerViewCart);
        tvTotalPrice = findViewById(R.id.totalPriceTextView);

        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new CartAdapter(this.arrayList, this, this, true);
        rvCartItems.setAdapter(cartAdapter);
        cartViewModel.getCart();

        // Observador para manejar la expiración de sesión
        cartViewModel.getSessionExpiredLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSessionExpired) {
                if (isSessionExpired != null && isSessionExpired) {
                    new AlertDialog.Builder(CartActivity.this)
                            .setTitle("Sesión expirada")
                            .setMessage("Tu sesión ha expirado. Por favor, inicia sesión nuevamente.")
                            .setCancelable(false)
                            .setPositiveButton("Iniciar sesión", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(CartActivity.this, AuthActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        });

        // Observador para obtener el carrito
        cartViewModel.getCartLiveData().observe(this, new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart != null) {
                    if (cart.getItems().isEmpty()) {
                        cartAdapter.setCart(new ArrayList<>());
                        updateTotalPrice(new ArrayList<>());
                        Toast.makeText(CartActivity.this, "Carrito vacío o eliminado", Toast.LENGTH_SHORT).show();
                        checkoutButton.setEnabled(false);
                    } else {
                        cartAdapter.setCart(cart.getItems());
                        updateTotalPrice(cart.getItems());
                        checkoutButton.setEnabled(true);
                    }

                } else {
                    checkoutButton.setEnabled(false);
                }
            }
        });

        cartViewModel.getErrorLiveData().observe(this, error -> {
            Log.i("MENSAJE", "onResponse:Error " + error);
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(CartActivity.this, PurchaseActivity.class);
                startActivity(x);
            }
        });
    }





    private void updateTotalPrice(List<CartDetail> products) {
        double total = 0.0;
        for (CartDetail product : products) {
            total += product.getProducto().getPrecio() * product.getCantidad();
        }
        tvTotalPrice.setText("Total: " + total);
    }

    @Override
    public void onProductClick(String productId) {
        cartViewModel.removeProductFromCart(Integer.parseInt(productId));
        Toast.makeText(this, "Producto Eliminado", Toast.LENGTH_SHORT).show();
    }

}
