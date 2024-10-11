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
import com.example.tiendafull.UI.Models.Products;

import java.util.List;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.AdaptadorProductHolder> {

    private List<Products> productList;
    private Context context;
    private OnProductClickListener listener;

    public AdaptadorProducto(List<Products> productList, Context context, OnProductClickListener listener) {
        this.productList = productList;
        this.context= context;
        this.listener= listener;

    }
    @NonNull
    @Override
    public AdaptadorProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto, parent, false);
        return new AdaptadorProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductHolder holder, int position) {
        holder.imprimir(position);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateProductList(List<Products> newProducts) {
        productList.clear(); // Limpiamos la lista actual
        productList.addAll(newProducts); // Agregamos los nuevos productos
        notifyDataSetChanged(); // Notificamos al adaptador que los datos han cambiado
    }

    public class AdaptadorProductHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2,tv3;
        ImageView iv1;
        Button button;
        public AdaptadorProductHolder(@NonNull View itemView) {
            super(itemView);
            tv1= itemView.findViewById(R.id.tv1);
            tv2= itemView.findViewById(R.id.tv2);
            tv3= itemView.findViewById(R.id.tv3);
            iv1=itemView.findViewById(R.id.iv1);
            button=itemView.findViewById(R.id.goToDetail);
        }

        public void imprimir(int position) {
            Products product = productList.get(position);
            tv1.setText("Nombre: "+product.getModelo());
            tv2.setText("Descripcion: "+product.getDetalle());
            tv3.setText("Precio: "+product.getPrecio());

            Glide.with(itemView.getContext())
                    .load(product.getImagen())
                    .placeholder(R.drawable.ic_launcher_foreground) // Imagen mostrada mientras carga
                    .error(R.drawable.ic_launcher_foreground) // Imagen mostrada si ocurre un error
                    .into(iv1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onProductClick(String.valueOf(product.getId()));


                }
            });


        }

    }
    public interface OnProductClickListener {
        void onProductClick(String productId);
    }


}