package com.example.tiendafull.UI.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;

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
    private ArrayList<CartDetail> arrayList =new ArrayList<>();
    private TextView tvTotalPrice;
    private Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        // Llamada para mostrar el AlertDialog
        showAlertDialog();
        b1= findViewById(R.id.checkoutButton);
        rvCartItems = findViewById(R.id.recyclerViewCart);
        tvTotalPrice = findViewById(R.id.totalPriceTextView);

        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el ViewModel
        SessionManager sessionManager = SessionManager.getInstance(this);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.setSessionManager(sessionManager);
        cartAdapter = new CartAdapter(this.arrayList, this,this);
        rvCartItems.setAdapter(cartAdapter);

        // Obtener el carrito al iniciar la actividad
        cartViewModel.getCart();

        // Observar los cambios en el carrito
        cartViewModel.getCartLiveData().observe(this, new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart != null) {
                    Log.i("MENSAJE", "onResponse: ok"+cart);
                    cartAdapter.setCart(cart.getItems());
                    updateTotalPrice(cart.getItems());
                }
            }
        });

        // Observar errores
        cartViewModel.getErrorLiveData().observe(this, error -> {
            // Mostrar mensaje de error (puedes usar un Toast o un Snackbar)
            Log.i("MENSAJE", "onResponse:Error "+error);
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this, "COMPRA FINALIZADA", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void showAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)  // Establecer el layout personalizado
                .setCancelable(false)  // Evitar que se cierre si se toca fuera del diálogo
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Acción al hacer clic en el botón
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
        Toast.makeText(this, "Producto Eliminad", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btnCancelarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CartActivity.this)
                        .setTitle("Cancelar Compra")
                        .setMessage("¿Estás seguro que deseas cancelar toda la compra?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            cartViewModel.clearCart();
                            cartAdapter.notifyDataSetChanged();
                            Toast.makeText(CartActivity.this, "Compra cancelada", Toast_LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

}