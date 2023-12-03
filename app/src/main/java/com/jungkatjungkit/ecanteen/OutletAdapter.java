package com.jungkatjungkit.ecanteen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.config.OutletData;

import java.util.List;

public class OutletAdapter extends RecyclerView.Adapter<OutletAdapter.ViewHolder> {
    private List<OutletData> outletList;

    public OutletAdapter(List<OutletData> outletList) {
        this.outletList = outletList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outlet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OutletData outletData = outletList.get(position);

        // Update the ViewHolder with the data
        holder.namaOutlet.setText(outletData.getNama_outlet());

        String imageName = outletData.getFoto(); // Assuming "kantin1", "kantin2", etc.
        int imageResId = holder.itemView.getContext().getResources()
                .getIdentifier(imageName, "drawable",
                        holder.itemView.getContext().getPackageName());

        // Set the image resource for the ImageView
        holder.foto.setImageResource(imageResId);
    }

    @Override
    public int getItemCount() {
        return outletList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaOutlet;
        ImageView foto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaOutlet = itemView.findViewById(R.id.outletNameLeft);
            foto = itemView.findViewById(R.id.outletImageLeft);
        }
    }
}