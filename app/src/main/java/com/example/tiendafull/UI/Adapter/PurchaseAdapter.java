package com.example.tiendafull.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.Purchase;

import java.util.List;


public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {

    private List<Purchase> purchaseList;
    private Context context;

    public PurchaseAdapter(List<Purchase> purchaseList, Context context) {
        this.purchaseList = purchaseList;
        this.context=context;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase, parent, false);
        return new PurchaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        Purchase purchase = purchaseList.get(position);
        holder.totalTextView.setText("Total: $" + purchase.getTotal());
        holder.invoiceTextView.setText("Invoice: " + purchase.getNro_factura());
        holder.dateTextView.setText("Date: " + purchase.getFecha());
        holder.userTextView.setText("User: " + purchase.getEmail());
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    static class PurchaseViewHolder extends RecyclerView.ViewHolder {
        TextView totalTextView, invoiceTextView, dateTextView, userTextView;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            invoiceTextView = itemView.findViewById(R.id.invoiceTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            userTextView = itemView.findViewById(R.id.userTextView);
        }
    }

    public void setPurchaseList(List<Purchase> purchases) {
        this.purchaseList = purchases;
        notifyDataSetChanged();
    }
}
