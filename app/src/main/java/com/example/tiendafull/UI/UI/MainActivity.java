package com.example.tiendafull.UI.UI;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Adapter.AdaptadorProducto;
import com.example.tiendafull.UI.Data.Data;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdaptadorProducto adaptadorProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return
                    insets;
        });


        recyclerView = findViewById(R.id.rv1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adaptadorProducto = new AdaptadorProducto();
        recyclerView.setAdapter(adaptadorProducto);
        Data.initializeProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem
                                                 item) {
        int nro = item.getItemId();
        if (nro == R.id.entrar) {
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        } else if (nro == R.id.productos) {
            return true;
        } else if (nro == R.id.contacto) {
            startActivity(new Intent(this, ContactActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem loginItem = menu.findItem(R.id.entrar);
        MenuItem productoItem = menu.findItem(R.id.productos);
        MenuItem contactoItem = menu.findItem(R.id.contacto);


        loginItem.setVisible(true);
        productoItem.setVisible(true);
        contactoItem.setVisible(true);

        if (this.getClass().equals(LoginActivity.class)) {
            loginItem.setVisible(false);
        }
        if (this.getClass().equals(MainActivity.class)) {
            productoItem.setVisible(false);
        }
        if (this.getClass().equals(ContactActivity.class)) {
            contactoItem.setVisible(false);
        }
        return true;
    }
}