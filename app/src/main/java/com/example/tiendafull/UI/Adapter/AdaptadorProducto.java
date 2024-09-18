package com.example.tiendafull.UI.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Data.Data;
import com.example.tiendafull.UI.Data.Products;
import com.example.tiendafull.UI.UI.DetailActivity;

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

    public class AdaptadorProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv1, tv2, tv3;
        ImageView iv1;

        public AdaptadorProductHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            iv1 = itemView.findViewById(R.id.iv1);

            itemView.setOnClickListener(this);
        }

        public void imprimir(int position) {
            Products producto = Data.products.get(position);

            tv1.setText("Nombre: " + producto.getModelo());
            tv2.setText("Descripción: " + producto.getDetalle());
            tv3.setText("Precio: " + producto.getPrecio());
            iv1.setImageResource(producto.getImagen());
        }

        @Override
        public void onClick(View v) {
            
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                
                Products productoSeleccionado = Data.products.get(position);

                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                intent.putExtra("nombre", productoSeleccionado.getModelo());
                intent.putExtra("precio", productoSeleccionado.getPrecio());
                intent.putExtra("material", productoSeleccionado.getMaterial());
                intent.putExtra("marca", productoSeleccionado.getMarca());
                intent.putExtra("estilo", productoSeleccionado.getEstilo());
                intent.putExtra("color", productoSeleccionado.getColor());
                intent.putExtra("rodado", productoSeleccionado.getRodado());
                intent.putExtra("descripcion", productoSeleccionado.getDetalle());
                intent.putExtra("imagen", productoSeleccionado.getImagen());

                v.getContext().startActivity(intent);
            }
        }
    }
}
