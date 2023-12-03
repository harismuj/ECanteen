package com.jungkatjungkit.ecanteen;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.config.GetOutlet;
import com.jungkatjungkit.ecanteen.config.OutletData;
import com.jungkatjungkit.ecanteen.config.apiURL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private GetOutlet getOutlet;

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

        fetchData();

        RecyclerView outletList = findViewById(R.id.outletList);
        outletList.setLayoutManager(new GridLayoutManager(this, 1));

        // Set a custom span size lookup to make items span two rows
        ((GridLayoutManager) outletList.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1; // Each item occupies one span (column)
            }
        });
        outletList.setAdapter(new OutletAdapter());
    }

    private void fetchData() {
        // Gunakan objek apiService untuk melakukan panggilan jaringan
        Call<OutletData> call = getOutlet.getData();
        call.enqueue(new Callback<OutletData>() {
            @Override
            public void onResponse(Call<OutletData> call, Response<OutletData> response) {
                if (response.isSuccessful()) {
                    OutletData outletData = response.body();

                    int outletId = outletData.getOutlet_id();
                    String namaOutlet = outletData.getNama_outlet();
                    int jumlahMenu = outletData.getJumlah_menu();
                    String foto = outletData.getFoto();
                    // Proses data di sini
                } else {
                    // Tangani kesalahan
                }
            }

            @Override
            public void onFailure(Call<OutletData> call, Throwable t) {
                // Tangani kegagalan
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
