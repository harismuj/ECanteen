package com.jungkatjungkit.ecanteen.config.riwayat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class OngoingOrderAdapter extends RecyclerView.Adapter<OngoingOrderAdapter.ViewHolder> {

    private List<OrderResponse> orderList;

    public OngoingOrderAdapter() {
        // Initialize an empty list or provide a default list if needed
        this.orderList = null;
    }

    public void setOrderList(List<OrderResponse> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_ongoing, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (orderList != null && position < orderList.size()) {
            OrderResponse order = orderList.get(position);
            holder.bind(order);
        }
    }

    @Override
    public int getItemCount() {
        return (orderList != null) ? orderList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView total, namaOutlet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaOutlet = itemView.findViewById(R.id.namaOutletOngoing);
            image = itemView.findViewById(R.id.imageOngoing);
            total = itemView.findViewById(R.id.totalOngoing);

        }

        public void bind(OrderResponse order) {
            String fotoOutletName = order.getFotoOutlet();
            namaOutlet.setText(order.getNamaOutlet());
            int fotoOutletResourceId = itemView.getContext().getResources().getIdentifier(fotoOutletName, "drawable", itemView.getContext().getPackageName());

            image.setImageResource(fotoOutletResourceId);
            // Format total menjadi Rupiah
            double totalAmount = order.getTotal();
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            currencyFormat.setCurrency(Currency.getInstance("IDR"));
            String formattedTotal = currencyFormat.format(totalAmount);
            total.setText(formattedTotal);
        }
    }
}
