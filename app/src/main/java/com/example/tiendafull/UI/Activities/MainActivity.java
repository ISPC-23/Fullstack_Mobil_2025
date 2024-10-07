package com.example.tiendafull.UI.Activities;
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

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Adapter.AdaptadorProducto;
import com.example.tiendafull.UI.Models.Products;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdaptadorProducto adaptadorProducto;
    private ArrayList<Products> listaproducts=new ArrayList<>();
    private ProductViewModel productViewModel;


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
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        SessionManager sessionManager = SessionManager.getInstance(this);
        productViewModel.setSessionManager(sessionManager);

        adaptadorProducto = new AdaptadorProducto(this.listaproducts, this);
        recyclerView.setAdapter(adaptadorProducto);
        productViewModel.fetchAllProducts();

        productViewModel.getProductListLiveData().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {

                if (products != null) {

                    adaptadorProducto.updateProductList(products);

                }
            }

        });
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
            startActivity(new Intent(this, LoginActivity.class)); // Assuming LoginActivity exists
            return true;
        } else if (nro == R.id.productos) {
            // Already in MainActivity, no need for intent
            return true;
        } else if (nro == R.id.contacto) {
            startActivity(new Intent(this, ContactActivity.class)); // Assuming ContactActivity exists
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void irAlDetalle(View v) {
        Intent intento = new Intent(this, DetailActivity.class);
        startActivity(intento);
    }
}