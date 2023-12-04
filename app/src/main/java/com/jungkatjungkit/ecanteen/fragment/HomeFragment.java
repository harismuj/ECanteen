package com.jungkatjungkit.ecanteen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.config.Category;
import com.jungkatjungkit.ecanteen.config.CategoryAdapter;
import com.jungkatjungkit.ecanteen.config.DateConverter;
import com.jungkatjungkit.ecanteen.config.apiURL;
import com.jungkatjungkit.ecanteen.config.outlet.GetOutlet;
import com.jungkatjungkit.ecanteen.config.outlet.OutletAdapter;
import com.jungkatjungkit.ecanteen.config.outlet.OutletData;
import com.jungkatjungkit.ecanteen.config.pesanan.Client;
import com.jungkatjungkit.ecanteen.config.pesanan.OrderApiService;
import com.jungkatjungkit.ecanteen.config.pesanan.OrderResponse;
import androidx.recyclerview.widget.LinearLayoutManager;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    public GetOutlet getOutlet;
    public OutletAdapter outletAdapter;
    private RecyclerView outletListRecyclerView;

    private static final String ARG_EMAIL = "ARG_EMAIL";

    public static HomeFragment newInstance(String email) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize and display the RecyclerView (category section)
        initCategorySection(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiURL.url() + "outlet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Buat objek ApiService menggunakan Retrofit
        getOutlet = retrofit.create(GetOutlet.class);

        outletAdapter = new OutletAdapter(new ArrayList<>(), requireContext());

        outletListRecyclerView = view.findViewById(R.id.outletList);
        outletListRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        // Set a custom span size lookup to make items span two rows
        ((GridLayoutManager) outletListRecyclerView.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1; // Each item occupies one span (column)
            }
        });

        outletListRecyclerView.setAdapter(outletAdapter);
        fetchData(view);
        latestBuy(view);

        return view;
    }

    // ... (Rest of the existing code)

    // Example method to initialize and display the category section
    private void initCategorySection(View view) {
        RecyclerView categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);

        // Create a list of categories (replace these with your actual data)
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(R.drawable.food));
        categories.add(new Category(R.drawable.drink));
        categories.add(new Category(R.drawable.snack));

        // Set up the RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setHasFixedSize(true); // Disable scrolling

        // Create and set the adapter for the RecyclerView
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, requireContext());
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void latestBuy(View view) {

        OrderApiService orderApiService = Client.getOrderApiService();
        // Mendapatkan data dari Intent
        Intent intent = getActivity().getIntent();
        String email = intent.getStringExtra("KEY_EMAIL");

            Log.d("HomeFragment", "email: " + email);
            Call<List<OrderResponse>> call = orderApiService.getLatestOrders(email, 1);
            call.enqueue(new Callback<List<OrderResponse>>() {
                @Override
                public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                    if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                        List<OrderResponse> orderResponses = response.body();
                        OrderResponse firstOrder = orderResponses.get(0);

                        // Now you can work with the first OrderResponse in the list
                        String fotoOutletName = firstOrder.getFotoOutlet();
                        int fotoOutletResourceId = getResources().getIdentifier(fotoOutletName, "drawable", requireContext().getPackageName());

                        ImageView imageTerakhir = view.findViewById(R.id.imageTerakhir);
                        TextView outletTerakhir, tanggalTerakhir, itemTerakhir, jumlahTerakhir;
                        outletTerakhir = view.findViewById(R.id.outletTerakhir);
                        tanggalTerakhir = view.findViewById(R.id.tanggalTerakhir);
                        itemTerakhir = view.findViewById(R.id.itemTerakhir);
                        jumlahTerakhir = view.findViewById(R.id.jumlahTerakhir);

                        outletTerakhir.setVisibility(View.VISIBLE);
                        tanggalTerakhir.setVisibility(View.VISIBLE);
                        itemTerakhir.setVisibility(View.VISIBLE);
                        jumlahTerakhir.setVisibility(View.VISIBLE);
                        imageTerakhir.setVisibility(View.VISIBLE);

                        outletTerakhir.setText(firstOrder.getNamaOutlet());

                        tanggalTerakhir.setText(DateConverter.convertDateString(String.valueOf(firstOrder.getTanggalPesanan())));
                        itemTerakhir.setText(firstOrder.getNamaMenu());
                        jumlahTerakhir.setText((firstOrder.getJumlahPesanan()) + " Pesanan");
                        imageTerakhir.setImageResource(fotoOutletResourceId);

                    } else {
                        TextView noOrder = view.findViewById(R.id.tvBelumAdaPesanan);
                        noOrder.setVisibility(View.VISIBLE);
                        // Handle unsuccessful response or empty list
                        Log.e("HomeFragment", "Response not successful or empty");
                    }
                }

                @Override
                public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                    // Handle failure here (e.g., log an error)
                    Log.e("HomeFragment", "Request failed: " + t.getMessage());
                }
            });

    }

    private void fetchData(View view) {
        // Use the apiService object to make a network call
        Call<List<OutletData>> call = getOutlet.getData();
        call.enqueue(new Callback<List<OutletData>>() {
            @Override
            public void onResponse(Call<List<OutletData>> call, Response<List<OutletData>> response) {
                if (response.isSuccessful()) {
                    List<OutletData> outletList = response.body();
                    Log.d("HomeFragment", "Outlet List Size: " + outletList.size());

                    // Update the data in the existing adapter
                    outletAdapter.updateData(outletList);
                } else {
                    // Handle unsuccessful response
                    Log.e("HomeFragment", "Unsuccessful response: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<OutletData>> call, Throwable t) {
                // Handle network call failure
            }
        });
    }
}
