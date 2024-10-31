package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.SessionManager;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar1;
    private MenuItem carritoItem; // Referencia al ítem del carrito en el menú
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sessionManager = SessionManager.getInstance(this); // Inicializar SessionManager

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        boolean shouldLogout = getIntent().getBooleanExtra("LOGOUT", false);
        boolean showPurchasesFragment = getIntent().getBooleanExtra("SHOW_PURCHASES_FRAGMENT", false);

        if (shouldLogout) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame3, new LogoutFragment())
                    .commit();
        } else if (showPurchasesFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame3, new UserPurchasesFragment())
                    .commit();
        } else if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame3, new ProductFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        carritoItem = menu.findItem(R.id.carrito); // Obtener referencia al ítem del carrito
        updateCartIconColor(); // Llamada para actualizar el color del ícono al iniciar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.productos) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame3, new ProductFragment()).commit();
            return true;
        } else if (item.getItemId() == R.id.salir) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame3, new LogoutFragment()).commit();
            return true;
        } else if (item.getItemId() == R.id.compras) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame3, new UserPurchasesFragment()).commit();
            return true;
        } else if (item.getItemId() == R.id.carrito) {
            Intent x = new Intent(this, CartActivity.class);
            startActivity(x);
            return true;
        } else if (item.getItemId() == R.id.contacto) {
            Intent intent = new Intent(this, ContactActivity.class);
            intent.putExtra("CONTACTO", true);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Método para actualizar el color del ícono del carrito
    public void updateCartIconColor() {
        if (carritoItem != null) {
            Drawable icon = carritoItem.getIcon();
            if (icon != null) {
                // Verificar la cantidad de productos en el carrito
                if (sessionManager.getCartProductCount() > 0) {
                    // Si hay productos en el carrito, el ícono se muestra en rojo
                    icon.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                } else {
                    // Si el carrito está vacío, el ícono se muestra en negro
                    icon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar el ícono del carrito cada vez que MainActivity vuelva a primer plano
        updateCartIconColor();
    }
}
