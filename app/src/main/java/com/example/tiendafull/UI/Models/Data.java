package com.example.tiendafull.UI.Models;

import com.example.tiendafull.R;

import java.util.ArrayList;

public class Data {

    public static ArrayList<Products> products = new ArrayList<>();

    public static void initializeProducts() {
        products.add(new Products(
                "Frida",
                349999,
                24,
                R.drawable.bici1,
                "Frida viene a revolucionar el mercado del entry level, con su cómodo cuadro podrás...",
                "Venzo",
                29,
                "Mountain",
                "Aluminio",
                "Rojo"
        ));

        products.add(new Products(
                "Wave",
                300000,
                105,
                R.drawable.bici2,
                "Bicicleta de montaña ligera ideal para quien comienza en la aventura",
                "Oxea",
                26,
                "Mountain",
                "Carbono",
                "Negro"
        ));

        products.add(new Products(
                "Raptor",
                790000,
                15,
                R.drawable.bici3,
                "La bicicleta está equipada con un cuadro Venzo Raptor EXO de aluminio 6061, que proporciona una excelente combinación de ligereza y resistencia.",
                "Venzo",
                29,
                "Mountain",
                "Acero",
                "Azul"
        ));

        products.add(new Products(
                "Tarmac SL7",
                4300000,
                8,
                R.drawable.bici4,
                " Bicicletas de carretera de rendimiento probado en campeonatos para profesionales, principiantes y todos los demás",
                "Specialized",
                29,
                "Carretera",
                "Carbono",
                "Verde"
        ));
    }
}
