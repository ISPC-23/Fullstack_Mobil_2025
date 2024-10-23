package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.tiendafull.R;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
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

}