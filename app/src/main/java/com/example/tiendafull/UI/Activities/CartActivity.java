package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Adapter.CartAdapter;
import com.example.tiendafull.UI.Models.Cart;
import com.example.tiendafull.UI.Models.CartDetail;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnProductCartClickListener {
    private CartViewModel cartViewModel;
    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private ArrayList<CartDetail> arrayList = new ArrayList<>();
    private TextView tvTotalPrice;
    private Button b1, b2;
    private Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);

        b1 = findViewById(R.id.checkoutButton);
        b2 = findViewById(R.id.btnCancelarCompra);
        rvCartItems = findViewById(R.id.recyclerViewCart);
        tvTotalPrice = findViewById(R.id.totalPriceTextView);

        rvCartItems.setLayoutManager(new LinearLayoutManager(this));


        SessionManager sessionManager = SessionManager.getInstance(this);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.setSessionManager(sessionManager);
        cartAdapter = new CartAdapter(this.arrayList, this, this, true);
        rvCartItems.setAdapter(cartAdapter);


        cartViewModel.getCart();


        cartViewModel.getCartLiveData().observe(this, new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart != null) {
                    Log.i("MENSAJE", "onResponse: ok" + cart);
                    cartAdapter.setCart(cart.getItems());
                    updateTotalPrice(cart.getItems());
                }
                if (cart != null && cart.getItems().isEmpty()) {
                    // El carrito está vacío o se eliminó
                    cartAdapter.setCart(new ArrayList<>()); // Limpiar la lista del adapter
                    updateTotalPrice(new ArrayList<>()); // Actualizar el precio total
                    Toast.makeText(CartActivity.this, "Carrito vacío o eliminado", Toast.LENGTH_SHORT).show();
                }
            }

        });


        cartViewModel.getErrorLiveData().observe(this, error -> {
            // Mostrar mensaje de error (puedes usar un Toast o un Snackbar)
            Log.i("MENSAJE", "onResponse:Error " + error);
        });

       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent x =new Intent(CartActivity.this, PurchaseActivity.class);
               startActivity(x);
           }
       });

        b2.setOnClickListener(v -> new AlertDialog.Builder(CartActivity.this)
                .setTitle("Cancelar Compra")
                .setMessage("¿Estás seguro que deseas cancelar toda la compra?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    cartViewModel.deleteCart();
                    Intent x =new Intent(this, MainActivity.class);
                    startActivity(x);


                    Toast.makeText(CartActivity.this, "Compra cancelada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.productos) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.salir) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("LOGOUT", true); // Envía una bandera para indicar que queremos ir al LogoutFragment
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateTotalPrice(List<CartDetail> products) {
        double total = 0.0;
        for (CartDetail product : products) {
            total += product.getProducto().getPrecio() * product.getCantidad();
        }
        tvTotalPrice.setText("Total: " + total);
    }

    @Override
    public void onProductClick(String productId) {
        cartViewModel.removeProductFromCart(Integer.parseInt(productId));
        Toast.makeText(this, "Producto Eliminado", Toast.LENGTH_SHORT).show();
}
}