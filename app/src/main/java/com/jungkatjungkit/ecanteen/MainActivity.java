package com.jungkatjungkit.ecanteen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.config.DateConverter;
import com.jungkatjungkit.ecanteen.config.outlet.GetOutlet;
import com.jungkatjungkit.ecanteen.config.outlet.OutletAdapter;
import com.jungkatjungkit.ecanteen.config.outlet.OutletData;
import com.jungkatjungkit.ecanteen.config.apiURL;
import com.jungkatjungkit.ecanteen.config.pesanan.Client;
import com.jungkatjungkit.ecanteen.config.pesanan.OrderApiService;
import com.jungkatjungkit.ecanteen.config.pesanan.OrderResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private GetOutlet getOutlet;
    private OutletAdapter outletAdapter;
    private RecyclerView outletListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Initialize and display the RecyclerView (category section)
        initCategorySection();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiURL.url()+"outlet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Buat objek ApiService menggunakan Retrofit
        getOutlet = retrofit.create(GetOutlet.class);

        outletAdapter = new OutletAdapter(new ArrayList<>(), this);

        outletListRecyclerView = findViewById(R.id.outletList);
        outletListRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Set a custom span size lookup to make items span two rows
        ((GridLayoutManager) outletListRecyclerView.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1; // Each item occupies one span (column)
            }
        });

        outletListRecyclerView.setAdapter(outletAdapter);
        fetchData();
        latestBuy();
    }

    private void latestBuy() {
        Intent getEmail = getIntent();
        String email = getEmail.getStringExtra("KEY_EMAIL");
        Log.d("MainActivity", "email: " + email);

        OrderApiService orderApiService = Client.getOrderApiService();

        Call<List<OrderResponse>> call = orderApiService.getLatestOrders(email, 1);
        call.enqueue(new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<OrderResponse> orderResponses = response.body();
                    OrderResponse firstOrder = orderResponses.get(0);

                    // Now you can work with the first OrderResponse in the list
                    String fotoOutletName = firstOrder.getFotoOutlet();
                    int fotoOutletResourceId = getResources().getIdentifier(fotoOutletName, "drawable", getPackageName());

                    ImageView imageTerakhir = findViewById(R.id.imageTerakhir);
                    TextView outletTerakhir,tanggalTerakhir,itemTerakhir,jumlahTerakhir;
                    outletTerakhir = findViewById(R.id.outletTerakhir);
                    tanggalTerakhir = findViewById(R.id.tanggalTerakhir);
                    itemTerakhir = findViewById(R.id.itemTerakhir);
                    jumlahTerakhir = findViewById(R.id.jumlahTerakhir);

                    outletTerakhir.setText(firstOrder.getNamaOutlet());

                    tanggalTerakhir.setText(DateConverter.convertDateString(String.valueOf(firstOrder.getTanggalPesanan())));
                    itemTerakhir.setText(firstOrder.getNamaMenu());
                    jumlahTerakhir.setText((firstOrder.getJumlahPesanan())+" Pesanan");
                    imageTerakhir.setImageResource(fotoOutletResourceId);
                } else {
                    // Handle unsuccessful response or empty list
                    Log.e("MainActivity", "Response not successful or empty");
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                // Handle failure here (e.g., log an error)
                Log.e("MainActivity", "Request failed: " + t.getMessage());
            }
        });


    }

    private void fetchData() {
        // Use the apiService object to make a network call
        Call<List<OutletData>> call = getOutlet.getData();
        call.enqueue(new Callback<List<OutletData>>() {
            @Override
            public void onResponse(Call<List<OutletData>> call, Response<List<OutletData>> response) {
                if (response.isSuccessful()) {
                    List<OutletData> outletList = response.body();
                    Log.d("MainActivity", "Outlet List Size: " + outletList.size());

                    // Update the data in the existing adapter
                    outletAdapter.updateData(outletList);
                } else {
                    // Handle unsuccessful response
                    Log.e("MainActivity", "Unsuccessful response: " + response.message());
                }
            }


            @Override
            public void onFailure(Call<List<OutletData>> call, Throwable t) {
                // Handle network call failure
            }
        });
    }


    // Example method to initialize and display the category section
    private void initCategorySection() {
        RecyclerView categoryRecyclerView = findViewById(R.id.categoryRecyclerView);

        // Create a list of categories (replace these with your actual data)
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(R.drawable.food));
        categories.add(new Category(R.drawable.drink));
        categories.add(new Category(R.drawable.snack));

        // Set up the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setHasFixedSize(true); // Disable scrolling

        // Create and set the adapter for the RecyclerView
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }
}
