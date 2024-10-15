package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.PurchaseConfirmResponse;
import com.example.tiendafull.UI.Models.SessionManager;

public class ResumeFragment extends Fragment {
    private TextView purchaseIdTextView;
    private TextView purchaseDateTextView;
    private TextView totalAmountTextView;
    private TextView purchasedItemsTextView;
    private Button button;
    private SessionManager sessionManager;

    public ResumeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_resume, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        purchaseIdTextView = view.findViewById(R.id.purchaseIdTextView);
        purchaseDateTextView = view.findViewById(R.id.purchaseDateTextView);
        totalAmountTextView = view.findViewById(R.id.totalAmountTextView);
        purchasedItemsTextView = view.findViewById(R.id.purchasedItemsTextView);
        button = view.findViewById(R.id.compras);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame3, new UserPurchasesFragment())
                        .commit();
            }
        });
        sessionManager = SessionManager.getInstance(getContext());
        PurchaseConfirmResponse lastPurchase = sessionManager.getLastPurchase();

        if (lastPurchase != null) {
            purchaseIdTextView.setText("Purchase ID: " + lastPurchase.getPurchase().getId());
            purchaseDateTextView.setText("Numero de Factura: " + lastPurchase.getPurchase().getNro_factura());
            totalAmountTextView.setText("Total: $" + lastPurchase.getPurchase().getTotal());
            purchasedItemsTextView.setText("Fecha: " + lastPurchase.getPurchase().getFecha());
        }


        handleBackPress();
    }



    private void handleBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Intent intent = new Intent(requireActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}
