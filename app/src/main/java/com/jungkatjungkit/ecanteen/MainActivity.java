package com.jungkatjungkit.ecanteen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.config.ApiResponse;
import com.jungkatjungkit.ecanteen.config.ApiService;
import com.jungkatjungkit.ecanteen.config.apiURL;
import com.jungkatjungkit.ecanteen.config.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ImageButton btnprofil;
    TextView menu;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initilize btn
        btnprofil=findViewById(R.id.profilNavIcon);
        menu = findViewById(R.id.menu);
        //btn
        btnprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lakukan sesuatu ketika tata letak diklik
                Intent mvProfile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(mvProfile);
            }
        });

        // Initialize and display the RecyclerView (category section)
        initCategorySection();

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
        callUserData(menu);
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

    public void callUserData(TextView welcomeTextView) {
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("KEY_EMAIL");

        // Log user email for verification
        Log.d("UserEmail", "User Email: " + userEmail);

        // Inisialisasi Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiURL.url() + "user/") // Check this URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Inisialisasi ApiService
        ApiService apiService = retrofit.create(ApiService.class);

        // Panggil metode getUserDataByEmail dari ApiService dengan nilai parameter email
        Call<ApiResponse> call = apiService.getUserByEmail(userEmail);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                // Log request details
                Log.d("RetrofitRequest", "Request URL: " + call.request().url());
                Log.d("RetrofitRequest", "Request Headers: " + call.request().headers());
                Log.d("RetrofitRequest", "Request Body: " + call.request().body());

                // Log response details
                Log.d("RetrofitResponse", "Response Code: " + response.code());
                Log.d("RetrofitResponse", "Response Body: " + response.body());

                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    ApiResponse responseData = response.body();
                    if (responseData != null && responseData.getUsers() != null && !responseData.getUsers().isEmpty()) {
                        String nama = responseData.getUsers().get(0).getNama();
                        welcomeTextView.setText("Hello " + nama);
                    } else {
                        welcomeTextView.setText("User data not available");
                    }
                } else {
                    welcomeTextView.setText("Error in API response. Code: " + statusCode);
                    Log.e("RetrofitError", "API request failed with code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Log request details (if needed)
                Log.d("RetrofitRequest", "Request URL: " + call.request().url());
                Log.d("RetrofitRequest", "Request Headers: " + call.request().headers());
                Log.d("RetrofitRequest", "Request Body: " + call.request().body());


                // Log the entire stack trace of the Throwable
                Log.e("RetrofitFailure", "API request failed", t);

                // Rest of your failure handling code...
                welcomeTextView.setText("Failed to retrieve user data. Check logs for details.");
            }
        });
    }



}
