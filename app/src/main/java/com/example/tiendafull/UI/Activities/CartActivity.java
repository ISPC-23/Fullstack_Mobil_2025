package com.example.tiendafull.UI.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class CartActivity extends AppCompatActivity implements CartAdapter.OnProductCartClickListener {
    private CartViewModel cartViewModel;
    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private ArrayList<CartDetail> arrayList =new ArrayList<>();
    private TextView tvTotalPrice;
    private Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        b1= findViewById(R.id.checkoutButton);
        rvCartItems = findViewById(R.id.recyclerViewCart);
        tvTotalPrice = findViewById(R.id.totalPriceTextView);

        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el ViewModel
        SessionManager sessionManager = SessionManager.getInstance(this);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.setSessionManager(sessionManager);
        cartAdapter = new CartAdapter(this.arrayList, this,this);
        rvCartItems.setAdapter(cartAdapter);

        // Obtener el carrito al iniciar la actividad
        cartViewModel.getCart();

        // Observar los cambios en el carrito
        cartViewModel.getCartLiveData().observe(this, new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart != null) {
                    Log.i("MENSAJE", "onResponse: ok"+cart);
                    cartAdapter.setCart(cart.getItems());
                    updateTotalPrice(cart.getItems());
                }
            }
        });

        // Observar errores
        cartViewModel.getErrorLiveData().observe(this, error -> {
            // Mostrar mensaje de error (puedes usar un Toast o un Snackbar)
            Log.i("MENSAJE", "onResponse:Error "+error);
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this, "COMPRA FINALIZADA", Toast.LENGTH_SHORT).show();

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
        Toast.makeText(this, "Producto Eliminad", Toast.LENGTH_SHORT).show();

    }
}