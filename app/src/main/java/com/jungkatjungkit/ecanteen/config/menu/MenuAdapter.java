package com.jungkatjungkit.ecanteen.config.menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuResponse> menuList;
    private OnMenuClickListener onMenuClickListener;

    public MenuAdapter(List<MenuResponse> menuList, OnMenuClickListener onMenuClickListener) {
        this.menuList = menuList;
        this.onMenuClickListener = onMenuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuResponse menu = menuList.get(position);

        // Bind data to ViewHolder
        holder.menuName.setText(menu.getNamaMenu());
        holder.menuKategori.setText(menu.getNamaKategori());
        holder.harga.setText(String.valueOf("Rp. " + menu.getHarga()));
        Log.d("MenuAdapter", "Menu Name: " + menu.getNamaMenu());
        Log.d("MenuAdapter", "Foto Name: " + menu.getMenu_foto());

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
        return menuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        TextView menuKategori;
        TextView harga;
        ImageView foto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuName);
            menuKategori = itemView.findViewById(R.id.menuKategori);
            harga = itemView.findViewById(R.id.harga);
            foto = itemView.findViewById(R.id.menuImage);

            // Set the click listener on the itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // Get the clicked menu item
                            MenuResponse clickedMenu = menuList.get(position);
                            // Trigger the interface method
                            onMenuClickListener.onMenuClick(clickedMenu);
                        }
                    }
                }
            });
        }
    }

    public void updateData(List<MenuResponse> newMenuList) {
        menuList.clear();
        menuList.addAll(newMenuList);
        Log.d("MenuAdapter", "Menu List Size: " + menuList.size());
        notifyDataSetChanged();
    }

    public interface OnMenuClickListener {
        void onMenuClick(MenuResponse menu);
    }
}
