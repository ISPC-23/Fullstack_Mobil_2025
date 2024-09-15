package com.example.tiendafull.UI.Data;

import com.example.tiendafull.R;

import java.util.ArrayList;

public class Data {

    // Lista de productos
    public static ArrayList<Products> products = new ArrayList<>();

    // Método para inicializar productos
    public static void initializeProducts() {
        products.add(new Products(
                "Mountain Bike X1",
                500.99,
                10,
                R.drawable.bici1,
                "Una bicicleta de montaña de alto rendimiento",
                "Marca X",
                29,
                "Mountain",
                "Aluminio",
                "Rojo"
        ));

        products.add(new Products(
                "Road Bike R2",
                750.49,
                5,
                R.drawable.bici2,
                "Bicicleta de carretera ligera",
                "Marca Y",
                28,
                "Road",
                "Carbono",
                "Negro"
        ));

        products.add(new Products(
                "BMX Freestyle",
                300.00,
                15,
                R.drawable.bici3,
                "Ideal para trucos y saltos",
                "Marca Z",
                20,
                "BMX",
                "Acero",
                "Azul"
        ));

        products.add(new Products(
                "Bicicleta Urbana",
                450.00,
                8,
                R.drawable.bici4,
                "Perfecta para desplazarse por la ciudad",
                "Marca W",
                26,
                "Urbana",
                "Aluminio",
                "Verde"
        ));
    }
}
