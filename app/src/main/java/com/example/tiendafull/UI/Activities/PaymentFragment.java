package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Adapter.CartAdapter;
import com.example.tiendafull.UI.Models.Cart;
import com.example.tiendafull.UI.Models.CartDetail;
import com.example.tiendafull.UI.Models.PurchaseConfirmResponse;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.CartViewModel;
import com.example.tiendafull.UI.ViewModels.PurchaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment {
    private PurchaseViewModel purchaseViewModel;
    private CartViewModel cartViewModel;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalTextView;
    private Button confirmButton;
    private Button cancelButton;
    private RadioGroup radioGroup;
    private ArrayList<String> pagos = new ArrayList<>();

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagos.add("Tarjeta de Débito");
        pagos.add("Tarjeta de Crédito");

        recyclerView = view.findViewById(R.id.recyclerViewPurchaseItems);
        totalTextView = view.findViewById(R.id.totalPurchaseAmount);
        confirmButton = view.findViewById(R.id.confirmPurchaseButton);
        cancelButton = view.findViewById(R.id.btnCancelarCompra); // Añadir el botón de cancelación
        radioGroup = view.findViewById(R.id.radioGroupPaymentMethods);

        for (String pagos: pagos) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(pagos);
            radioGroup.addView(radioButton);

            if ("Tarjeta de Débito".equals(pagos)) {
                radioButton.setChecked(true);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter = new CartAdapter(new ArrayList<>(), getContext(), null, false);
        recyclerView.setAdapter(cartAdapter);
        SessionManager sessionManager = SessionManager.getInstance(getContext());
        purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
        purchaseViewModel.setSessionManager(sessionManager);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.setSessionManager(sessionManager);

        cartViewModel.getCartLiveData().observe(getViewLifecycleOwner(), new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart != null){
                    int total = cartViewModel.getCartTotal();
                    totalTextView.setText("Total: " + total);
                    cartAdapter.setCart(cart.getItems());
                }
            }
        });
        cartViewModel.getCart();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmButton.setEnabled(false);
                purchaseViewModel.confirmPurchase();
            }
        });

        cancelButton.setOnClickListener(v -> new AlertDialog.Builder(requireContext())
                .setTitle("Cancelar Compra")
                .setMessage("¿Estás seguro que deseas cancelar toda la compra?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    cartViewModel.deleteCart();  // Borra el carrito
                    Toast.makeText(getContext(), "Compra cancelada", Toast.LENGTH_SHORT).show();

                    Intent x = new Intent(requireActivity(), MainActivity.class); // Usa requireActivity() para obtener el contexto de la actividad
                    startActivity(x);
                })
                .setNegativeButton("No", null)
                .show()
        );


        purchaseViewModel.getPurchaseLiveData().observe(getViewLifecycleOwner(), new Observer<PurchaseConfirmResponse>() {
            @Override
            public void onChanged(PurchaseConfirmResponse purchaseConfirmResponse) {
                confirmButton.setEnabled(true);
                if (purchaseConfirmResponse != null) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame3, new ResumeFragment())
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        purchaseViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                confirmButton.setEnabled(true);
                Toast.makeText(getContext(), "Error: " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
