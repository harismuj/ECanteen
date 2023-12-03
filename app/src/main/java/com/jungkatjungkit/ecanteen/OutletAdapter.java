package com.jungkatjungkit.ecanteen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.PointerIcon;
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
    private Context context;

    public OutletAdapter(List<OutletData> outletList, Context context) {
        this.outletList = outletList;
        this.context = context;
    }
    public void updateData(List<OutletData> newList) {
        outletList.clear();
        outletList.addAll(newList);
        notifyDataSetChanged();
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
        holder.itemOutlet.setText(String.valueOf(outletData.getJumlah_menu()) + " Menu");

        String imageName = outletData.getFoto(); // Assuming "kantin1", "kantin2", etc.
        int imageResId = holder.itemView.getContext().getResources()
                .getIdentifier(imageName, "drawable",
                        holder.itemView.getContext().getPackageName());

        // Set the image resource for the ImageView
        holder.foto.setImageResource(imageResId);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the new activity here
                Intent intent = new Intent(context, OutletActivity.class);
                // Pass any data to the new activity if needed
                intent.putExtra("OUTLET_ID", outletData.getOutlet_id());
                intent.putExtra("OUTLET_NAME", outletData.getNama_outlet());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return outletList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaOutlet;
        TextView itemOutlet;
        ImageView foto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaOutlet = itemView.findViewById(R.id.outletNameLeft);
            itemOutlet = itemView.findViewById(R.id.outletItem);
            foto = itemView.findViewById(R.id.outletImageLeft);
        }
    }
}