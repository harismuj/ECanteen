package com.jungkatjungkit.ecanteen.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.activity.OutletActivity;
import com.jungkatjungkit.ecanteen.config.apiURL;
import com.jungkatjungkit.ecanteen.config.menu.MenuAdapter;
import com.jungkatjungkit.ecanteen.config.menu.MenuResponse;
import com.jungkatjungkit.ecanteen.config.order.OrderAdapter;
import com.jungkatjungkit.ecanteen.config.order.OrderItem;
import com.jungkatjungkit.ecanteen.config.order.OrderRequest;
import com.jungkatjungkit.ecanteen.config.order.OrderApiService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    private TextView outletNameTextView;
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private Button orderButton;
    private int totalOrderPrice = 0;
    public static OrderFragment newInstance(int outletId, String outletName) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt("OUTLET_ID", outletId);
        args.putString("OUTLET_NAME", outletName);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        outletNameTextView = view.findViewById(R.id.namaOutlet);
        orderRecyclerView = view.findViewById(R.id.orderRecyclerView);

        //Create an empty list to be used by the adapter
        List<MenuResponse> emptyList = new ArrayList<>();

        // Initialize the adapter with the empty list
        orderAdapter = new OrderAdapter(emptyList);

        // Set the adapter on the orderRecyclerView
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        orderRecyclerView.setAdapter(orderAdapter);
        Button orderButton = view.findViewById(R.id.orderButton);

        accessQuantityData();  // Call accessQuantityData to calculate totalOrderPrice

        TextView totalPriceTextView = view.findViewById(R.id.totalPriceTextView);
        totalPriceTextView.setText(getString(R.string.total_price, totalOrderPrice));

        Bundle args = getArguments();
        Intent intent = getActivity().getIntent();
        if (args != null && args.containsKey("OUTLET_NAME")) {
            int outletId = intent.getIntExtra("OUTLET_ID", -1);
            String outletName = args.getString("OUTLET_NAME");
            outletNameTextView.setText(outletName);

            // Fetch menu data for the specific outlet (replace -1 with actual outlet ID)
//            fetchMenuData(outletId);
        }

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create JSON payload using Retrofit model classes
                OrderRequest orderRequest = createOrderRequest();

                // Post JSON data using Retrofit
                postOrder(orderRequest);
            }
        });

        return view;
    }
    private void accessQuantityData() {
        if (getActivity() instanceof OutletActivity) {
            List<MenuResponse> quantityData = ((OutletActivity) getActivity()).getQuantityData();

            // Filter menus with quantity > 0
            List<MenuResponse> menusWithQuantity = new ArrayList<>();
            for (MenuResponse menu : quantityData) {
                if (menu.getQuantity() > 0) {
                    menusWithQuantity.add(menu);
                    totalOrderPrice += menu.getHarga();  // Update totalOrderPrice
                    Log.d("OrderFragment", "Updated totalOrderPrice: " + totalOrderPrice);
                }
            }

            // Log the size of menusWithQuantity
            Log.d("OrderFragment", "Menus with Quantity Size: " + menusWithQuantity);

            // Update existing adapter with new data
            if (orderAdapter != null) {
                orderAdapter.updateData(menusWithQuantity);
            } else {
                Log.e("OrderFragment", "OrderAdapter is null");
            }
        }
    }

    private OrderRequest createOrderRequest() {
        try {
            int userId = 5;
            String catatan = "gak pedes";
            List<MenuResponse> menusWithQuantity = ((OrderAdapter) orderRecyclerView.getAdapter()).getOrderList();
            Log.d("createOrder", String.valueOf(menusWithQuantity));
            // Create a list to hold order items
            List<OrderItem> orderItems = new ArrayList<>();
            int totalHarga = 0;

            for (MenuResponse menu : menusWithQuantity) {
                OrderItem orderItem = new OrderItem(menu.getMenuId(), menu.getQuantity(), menu.getHarga() * menu.getQuantity());
                orderItems.add(orderItem);
                totalHarga += menu.getHarga() * menu.getQuantity();
            }

            // Create the main OrderRequest
            return new OrderRequest(userId, catatan, orderItems, totalHarga);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void postOrder(OrderRequest orderRequest) {
        Log.d("", apiURL.url());
        // Inisialisasi Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiURL.url()) // Adjust the base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Inisialisasi orderApiService
        OrderApiService orderApiService = retrofit.create(OrderApiService.class);

        // Panggil metode postOrder dari orderApiService
        Call<Void> call = orderApiService.postOrder(orderRequest);

        // Proses permintaan
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(getContext(), "Order berhasil dikirim", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unsuccessful response
                    Log.e("OrderFragment", "Error: " + response.message());
                    Toast.makeText(getContext(), "Terjadi kesalahan saat mengirim order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Log.e("OrderFragment", "Failed to make API call", t);
                Toast.makeText(getContext(), "Gagal mengirim order. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}