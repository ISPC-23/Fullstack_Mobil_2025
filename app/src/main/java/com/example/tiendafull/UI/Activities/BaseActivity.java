package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Adapter.CartAdapter;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.CartViewModel;

public class BaseActivity extends AppCompatActivity {
    private MenuItem carritoItem; // Referencia al ítem del carrito en el menú
    public CartViewModel cartViewModel;
    private boolean hasProductsInCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_base);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbarx);
        setSupportActionBar(toolbar);

        SessionManager sessionManager = SessionManager.getInstance(this);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.setSessionManager(sessionManager);
        observeCartChanges();
        cartViewModel.getCart();

    }
    protected void setActivityContent(int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.frame_content);
        getLayoutInflater().inflate(layoutResID, contentFrame, true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        carritoItem = menu.findItem(R.id.carrito); // Obtener referencia al ítem del carrito// Llamada para actualizar el color del ícono al iniciar
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
        } else if (item.getItemId() == R.id.carrito) {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("CONTACTO", true);
        startActivity(intent);
        return true;
    }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        updateCartIconColor(hasProductsInCart);
        return super.onPrepareOptionsMenu(menu);

    }

    public void updateCartIconColor(Boolean hasProductsInCart) {
        if (carritoItem != null) {
            Drawable icon = carritoItem.getIcon();
            if (icon != null) {
                if (hasProductsInCart) {
                    // Si hay productos en el carrito, el ícono se muestra en rojo
                    icon.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                } else {
                    // Si el carrito está vacío, el ícono se muestra en negro
                    icon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }
    private void observeCartChanges() {
        cartViewModel.getCartLiveData().observe(this, cart -> {
            if (cart != null) {
                hasProductsInCart = !cart.getItems().isEmpty();
                invalidateOptionsMenu(); // Llama a onPrepareOptionsMenu para actualizar el icono de la Toolbar

            }
        });
    }




}