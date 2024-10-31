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

public class CartActivity extends AppCompatActivity implements CartAdapter.OnProductCartClickListener {
    private CartViewModel cartViewModel;
    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private ArrayList<CartDetail> arrayList = new ArrayList<>();
    private TextView tvTotalPrice;
    private Button checkoutButton;
    private Toolbar toolbar1;
    private MenuItem carritoItem; // Referencia al ítem del carrito en el menú

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);

        checkoutButton = findViewById(R.id.checkoutButton);
        rvCartItems = findViewById(R.id.recyclerViewCart);
        tvTotalPrice = findViewById(R.id.totalPriceTextView);

        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        SessionManager sessionManager = SessionManager.getInstance(this);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.setSessionManager(sessionManager);
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
                    updateCartIconColor(); // Actualizar el color del ícono al cambiar el carrito
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        carritoItem = menu.findItem(R.id.carrito); // Obtener referencia al ítem del carrito
        updateCartIconColor(); // Llamada para actualizar el color del ícono al iniciar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Lógica para los ítems del menú
        if (item.getItemId() == R.id.productos) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.compras) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("SHOW_PURCHASES_FRAGMENT", true);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.salir) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("LOGOUT", true);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.contacto) {
            Intent intent = new Intent(this, ContactActivity.class);
            intent.putExtra("CONTACTO", true);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateCartIconColor() {
        if (carritoItem != null) {
            Drawable icon = carritoItem.getIcon();
            if (icon != null) {
                if (arrayList.size() > 0) {
                    // Si hay productos en el carrito, el ícono se muestra en rojo
                    icon.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                } else {
                    // Si el carrito está vacío, el ícono se muestra en negro
                    icon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                }
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar el ícono del carrito cada vez que CartActivity vuelva a primer plano
        updateCartIconColor();
    }
}
