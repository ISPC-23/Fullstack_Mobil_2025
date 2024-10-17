package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendafull.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        // Verificar extras en el Intent para decidir qué fragmento mostrar
        boolean shouldLogout = getIntent().getBooleanExtra("LOGOUT", false);
        boolean showPurchasesFragment = getIntent().getBooleanExtra("SHOW_PURCHASES_FRAGMENT", false);

        if (shouldLogout) {
            // Mostrar el fragmento de Logout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame3, new LogoutFragment())
                    .commit();
        } else if (showPurchasesFragment) {
            // Mostrar el fragmento de UserPurchases
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame3, new UserPurchasesFragment())
                    .commit();
        } else if (savedInstanceState == null) {
            // Mostrar el fragmento predeterminado si no hay otra instrucción
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame3, new ProductFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
