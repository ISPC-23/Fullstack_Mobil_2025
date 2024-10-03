package com.example.tiendafull.UI.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String nombre = getIntent().getStringExtra("nombre");
        double precio = getIntent().getDoubleExtra("precio", 0.0);
        String material = getIntent().getStringExtra("material");
        String marca = getIntent().getStringExtra("marca");
        String estilo = getIntent().getStringExtra("estilo");
        String color = getIntent().getStringExtra("color");
        int rodado = getIntent().getIntExtra("rodado", 0);
        String descripcion = getIntent().getStringExtra("descripcion");
        int imagen = getIntent().getIntExtra("imagen", R.drawable.bici4);

        ImageView imgView = findViewById(R.id.img);
        TextView titleTextView = findViewById(R.id.title);
        TextView priceTextView = findViewById(R.id.price);
        TextView materialTextView = findViewById(R.id.tv1);
        TextView marcaTextView = findViewById(R.id.tv2);
        TextView estiloTextView = findViewById(R.id.tv3);
        TextView colorTextView = findViewById(R.id.tv4);
        TextView rodadoTextView = findViewById(R.id.tv5);
        TextView detailTextView = findViewById(R.id.detail);


        imgView.setImageResource(imagen);
        titleTextView.setText(nombre);
        priceTextView.setText("$" + String.valueOf(precio));
        materialTextView.setText(material);
        marcaTextView.setText(marca);
        estiloTextView.setText(estilo);
        colorTextView.setText(color);
        rodadoTextView.setText(String.valueOf(rodado));
        detailTextView.setText(descripcion);
    }

    public void retornar(View v){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
        if (this.getClass().equals(MainActivity.class) || this.getClass().equals(DetailActivity.class)) {
            productoItem.setVisible(false);
        }
        if (this.getClass().equals(ContactActivity.class)) {
            contactoItem.setVisible(false);
        }
        return true;
    }
}