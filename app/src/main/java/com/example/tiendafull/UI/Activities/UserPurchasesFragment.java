package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Adapter.PurchaseAdapter;
import com.example.tiendafull.UI.Models.Purchase;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.PurchaseViewModel;

import java.util.ArrayList;
import java.util.List;


public class UserPurchasesFragment extends Fragment {
    private PurchaseViewModel purchaseViewModel;
    private RecyclerView purchaseRecyclerView;
    private PurchaseAdapter purchaseAdapter;
    private ArrayList<Purchase> listacompras=new ArrayList<>();

    public UserPurchasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_purchases, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        purchaseRecyclerView = view.findViewById(R.id.purchase_recycler_view);
        purchaseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
        SessionManager sessionManager =SessionManager.getInstance(getContext());
        purchaseViewModel.setSessionManager(sessionManager);
        purchaseAdapter = new PurchaseAdapter(this.listacompras, getContext());
        purchaseRecyclerView.setAdapter(purchaseAdapter);

        purchaseViewModel.getPurchaseListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> purchases) {
                if (purchases != null) {
                    purchaseAdapter.setPurchaseList(purchases);
                }
            }
        });
        purchaseViewModel.fetchUserPurchases();
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