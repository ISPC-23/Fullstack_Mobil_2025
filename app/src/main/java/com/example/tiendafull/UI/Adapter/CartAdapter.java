package com.example.tiendafull.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tiendafull.R;
import com.example.tiendafull.UI.Activities.CartActivity;
import com.example.tiendafull.UI.Models.CartDetail;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartDetail> products;
    private Context context;
    private OnProductCartClickListener listener;

    public CartAdapter(List<CartDetail> products, Context context,OnProductCartClickListener listener) {
        this.products = products;
        this.context = context;
        this.listener=listener;// Guarda una referencia al CartViewModel
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.imprimir(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setCart(List<CartDetail> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2,tv3;
        ImageView iv1;
        Button button;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tvProductName);
            tv2 = itemView.findViewById(R.id.tvProductPrice);
            tv3 = itemView.findViewById(R.id.tvProductQuantity);
            iv1 = itemView.findViewById(R.id.ivProductImage);
            button = itemView.findViewById(R.id.buttonRemove);

            // Configurar el listener del botón de eliminar

        }

        public void imprimir(int position) {
            CartDetail product = products.get(position);
            tv1.setText("Nombre: " + product.getProducto().getModelo());
            tv2.setText("Descripción: " + product.getProducto().getDetalle());
            tv3.setText(("Cantidad: " + product.getCantidad() ));

            Glide.with(itemView.getContext())
                    .load(product.getProducto().getImagen())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(iv1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onProductClick(String.valueOf(product.getProducto().getId()));


                }
            });


        }

    }
    public interface OnProductCartClickListener {
        void onProductClick(String productId);
    }


}