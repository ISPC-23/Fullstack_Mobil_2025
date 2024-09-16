package com.example.tiendafull.UI.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Data.Data;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.AdaptadorProductHolder> {
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
        return Data.products.size();
    }

    public class AdaptadorProductHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2,tv3;
        ImageView iv1;
        public AdaptadorProductHolder(@NonNull View itemView) {
            super(itemView);
            tv1= itemView.findViewById(R.id.tv1);
            tv2= itemView.findViewById(R.id.tv2);
            tv3= itemView.findViewById(R.id.tv3);
            iv1=itemView.findViewById(R.id.iv1);
        }

        public void imprimir(int position) {
            tv1.setText("Nombre: "+Data.products.get(position).getModelo());
            tv2.setText("Descripcion: "+Data.products.get(position).getDetalle());
            tv3.setText("Precio: "+Data.products.get(position).getPrecio());
            iv1.setImageResource(Data.products.get(position).getImagen());
        }
    }
}
