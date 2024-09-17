package com.example.tiendafull.UI.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendafull.R;

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
        double precio = getIntent().getDoubleExtra("precio", 0.0);  // double para el precio
        String material = getIntent().getStringExtra("material");
        String marca = getIntent().getStringExtra("marca");
        String estilo = getIntent().getStringExtra("estilo");
        String color = getIntent().getStringExtra("color");
        int rodado = getIntent().getIntExtra("rodado", 0);  // int para el rodado
        String descripcion = getIntent().getStringExtra("descripcion");
        // int imagen = getIntent().getIntExtra("imagen", R.drawable.default_image);  // Imagen por defecto si no llega ninguna

        ImageView imgView = findViewById(R.id.img);
        TextView titleTextView = findViewById(R.id.title);
        TextView priceTextView = findViewById(R.id.price);
        TextView materialTextView = findViewById(R.id.tv1);
        TextView marcaTextView = findViewById(R.id.tv2);
        TextView estiloTextView = findViewById(R.id.tv3);
        TextView colorTextView = findViewById(R.id.tv4);
        TextView rodadoTextView = findViewById(R.id.tv5);
        TextView detailTextView = findViewById(R.id.detail);


        // imgView.setImageResource(imagen);  // Imagen del producto
        titleTextView.setText(nombre);  // Nombre del producto
        priceTextView.setText("$" + String.valueOf(precio));  // Precio del producto, formateado con el símbolo de $
        materialTextView.setText(material);  // Material del producto
        marcaTextView.setText(marca);  // Marca del producto
        estiloTextView.setText(estilo);  // Estilo del producto
        colorTextView.setText(color);  // Color del producto
        rodadoTextView.setText(String.valueOf(rodado));  // Rodado del producto
        detailTextView.setText(descripcion);  // Descripción del producto
    }

    public void retornar(View v){
        finish();
    }
}