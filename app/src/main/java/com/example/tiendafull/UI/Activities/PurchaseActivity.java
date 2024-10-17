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

public class PurchaseActivity extends AppCompatActivity {
    private Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_purchase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        toolbar1 = findViewById(R.id.toolbarPurchase);
        setSupportActionBar(toolbar1);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame3, new PaymentFragment())
                .commit();
    }

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
        }  else if (item.getItemId() == R.id.compras) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame3, new UserPurchasesFragment()).commit();
            return true;
        }
        else if (item.getItemId() == R.id.carrito) {
            Intent x =new Intent(this, CartActivity.class);
            startActivity(x);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}