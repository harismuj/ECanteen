package com.jungkatjungkit.ecanteen.config.order;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.config.menu.MenuResponse;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private int totalOrderPrice = 0;
    private List<MenuResponse> orderList;

    public OrderAdapter(List<MenuResponse> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuResponse menu = orderList.get(position);
        totalOrderPrice += menu.getHargaAsInt();
        // Bind data to ViewHolder
        holder.menuName.setText(menu.getNamaMenu());
        holder.quantity.setText("Jumlah: " + menu.getQuantity());
        holder.harga.setText("Rp. " + String.valueOf(menu.getHarga()));

        String imageName = menu.getMenu_foto(); // Assuming "kantin1", "kantin2", etc.
        int imageResId = holder.itemView.getContext().getResources()
                .getIdentifier(imageName, "drawable",
                        holder.itemView.getContext().getPackageName());

        if (imageResId != 0) {
            // Set the image resource for the ImageView
            holder.foto.setImageResource(imageResId);
        } else {
            // Log an error or provide a default image
            Log.e("MenuAdapter", "Failed to find drawable resource for: " + imageName);
            holder.foto.setImageResource(R.drawable.category); // Use a default image
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        ImageView foto;
        TextView harga;
        TextView quantity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuName);
            foto = itemView.findViewById(R.id.menuImage);
            quantity = itemView.findViewById(R.id.quantity);
            harga = itemView.findViewById(R.id.harga);
        }
    }

    public void updateData(List<MenuResponse> newOrderList) {
        orderList.clear();
        orderList.addAll(newOrderList);
        notifyDataSetChanged();
    }

    private List<MenuResponse> filterOrderList(List<MenuResponse> orderList) {
        List<MenuResponse> filteredList = new ArrayList<>();
        for (MenuResponse menu : orderList) {
            if (menu.getQuantity() > 0) {
                filteredList.add(menu);
            }
        }
        return filteredList;
    }
}
