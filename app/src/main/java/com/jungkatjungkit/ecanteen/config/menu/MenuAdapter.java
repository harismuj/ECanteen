package com.jungkatjungkit.ecanteen.config.menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuResponse> menuList;
    private OnButtonClickListener onButtonClickListener;

    public MenuAdapter(List<MenuResponse> menuList, OnButtonClickListener onButtonClickListener) {
        this.menuList = menuList;
        this.onButtonClickListener = onButtonClickListener;
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

        holder.quantity.setText(String.valueOf(menu.getQuantity()));

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on the '-' button for this specific menu item
                // You may want to decrement the quantity or perform some other action
                if (onButtonClickListener != null) {
                    onButtonClickListener.onMinusButtonClick(menu);
                }
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on the '+' button for this specific menu item
                // You may want to increment the quantity or perform some other action
                if (onButtonClickListener != null) {
                    onButtonClickListener.onPlusButtonClick(menu);
                }
            }
        });
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

        //quantity
        TextView quantity;
        ImageButton btnPlus;
        ImageButton btnMinus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuName);
            menuKategori = itemView.findViewById(R.id.menuKategori);
            harga = itemView.findViewById(R.id.harga);
            foto = itemView.findViewById(R.id.menuImage);
            quantity = itemView.findViewById(R.id.quantityTextView);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);

            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onButtonClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onButtonClickListener.onPlusButtonClick(menuList.get(position));
                            Log.d("MenuAdapter", "Quantity after plus click: " + menuList.get(position).getQuantity());
                        }
                    }
                }
            });
            // Set the click listener on the itemView
            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onButtonClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onButtonClickListener.onMinusButtonClick(menuList.get(position));
                            Log.d("MenuAdapter", "Quantity after minus click: " + menuList.get(position).getQuantity());
                        }
                    }
                }
            });
        }
    }
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
    public void updateData(List<MenuResponse> newMenuList) {
        menuList.clear();
        menuList.addAll(newMenuList);
        notifyDataSetChanged();
    }

    public interface OnButtonClickListener {
        void onPlusButtonClick(MenuResponse menu);
        void onMinusButtonClick(MenuResponse menu);
    }
}
