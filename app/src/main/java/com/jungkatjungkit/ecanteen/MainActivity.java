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

        outletAdapter = new OutletAdapter(new ArrayList<>());

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
    }

    private void fetchData() {
        // Use the apiService object to make a network call
        Call<List<OutletData>> call = getOutlet.getData();
        call.enqueue(new Callback<List<OutletData>>() {
            @Override
            public void onResponse(Call<List<OutletData>> call, Response<List<OutletData>> response) {
                if (response.isSuccessful()) {
                    List<OutletData> outletList = response.body();

                    // Initialize or update the adapter with the new data
                    outletAdapter = new OutletAdapter(outletList);
                    outletListRecyclerView.setAdapter(outletAdapter);
                } else {
                    // Handle unsuccessful response
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
