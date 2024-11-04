package com.example.tiendafull.UI.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.Purchase;
import java.util.List;


public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {

    private List<Purchase> purchaseList;
    private Context context;
    private OnCancelClickListener listener;

    public PurchaseAdapter(List<Purchase> purchaseList, Context context,OnCancelClickListener listener) {
        this.purchaseList = purchaseList;
        this.context=context;
        this.listener = listener;
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
        holder.canceladaTextView.setText("Cancelada: " + purchase.isEs_cancelada());
        if (purchase.isEs_cancelada()) {
            holder.cancelarCompra.setEnabled(false);
            holder.cancelarCompra.setText("Cancelado");
            holder.cancelarCompra.setAlpha(0.5f);
        } else {
            holder.cancelarCompra.setEnabled(true);
            holder.cancelarCompra.setText("Cancelar compra");
            holder.cancelarCompra.setAlpha(1f);
            holder.cancelarCompra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCancelClick(String.valueOf(purchase.getId()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    static class PurchaseViewHolder extends RecyclerView.ViewHolder {
        TextView totalTextView, invoiceTextView, dateTextView, userTextView, canceladaTextView;
        Button cancelarCompra;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            invoiceTextView = itemView.findViewById(R.id.invoiceTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            userTextView = itemView.findViewById(R.id.userTextView);
            canceladaTextView = itemView.findViewById(R.id.canceladaTextView);
            cancelarCompra = itemView.findViewById(R.id.cancelarCompra);
        }
    }

    public void setPurchaseList(List<Purchase> purchases) {
        this.purchaseList = purchases;
        notifyDataSetChanged();
    }

    public interface OnCancelClickListener {
        void onCancelClick(String Id);
}}
