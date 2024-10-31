package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.SessionManager;

public class ContactActivity extends AppCompatActivity {
    private MenuItem carritoItem; // Referencia al ítem del carrito en el menú
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        sessionManager = SessionManager.getInstance(this); // Inicializar SessionManager

        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.salir) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("LOGOUT", true);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.compras) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("SHOW_PURCHASES_FRAGMENT", true);
            startActivity(intent);
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
    private void updateCartIconColor() {
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
        // Actualizar el ícono del carrito cada vez que ContactActivity vuelva a primer plano
        updateCartIconColor();
    }
}
