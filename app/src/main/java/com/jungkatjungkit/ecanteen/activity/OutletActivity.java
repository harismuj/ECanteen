package com.jungkatjungkit.ecanteen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jungkatjungkit.ecanteen.MainActivity;
import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.config.menu.Menu;
import com.jungkatjungkit.ecanteen.config.menu.MenuAdapter;
import com.jungkatjungkit.ecanteen.config.menu.MenuApiService;
import com.jungkatjungkit.ecanteen.config.menu.MenuResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

//public class OutletActivity extends AppCompatActivity implements MenuAdapter.OnButtonClickListener {
//
//    private TextView outletNameTextView;
//    private RecyclerView menuRecyclerView;
//    private MenuAdapter menuAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_outlet);
//
//        outletNameTextView = findViewById(R.id.namaOutlet);
//        menuRecyclerView = findViewById(R.id.menuRecyclerView);
//
//        // Set up RecyclerView for the menu
//        menuAdapter = new MenuAdapter(new ArrayList<>(), this);
//        menuAdapter.setOnButtonClickListener(this);
//
//        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
////        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
////        menuRecyclerView.setLayoutManager(layoutManager);
//
//        menuRecyclerView.setAdapter(menuAdapter);
//
//        // Retrieve outlet details from Intent
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("OUTLET_ID") && intent.hasExtra("OUTLET_NAME")) {
//            int outletId = intent.getIntExtra("OUTLET_ID", -1);
//            String outletName = intent.getStringExtra("OUTLET_NAME");
//
//            outletNameTextView.setText(outletName);
//            Log.d("OutletActivity", "Outlet ID: " + outletId);
//            Log.d("OutletActivity", "Outlet Name: " + outletName);
//
//            // Fetch menu data for the specific outlet
//            fetchMenuData(outletId);
//        }
//    }
//
//    private void fetchMenuData(int outletId) {
//        MenuApiService menuApiService = Menu.getMenuApiService();
//        Call<List<MenuResponse>> call = menuApiService.getMenu(outletId);
//
//        call.enqueue(new Callback<List<MenuResponse>>() {
//            @Override
//            public void onResponse(Call<List<MenuResponse>> call, Response<List<MenuResponse>> response) {
//                Log.d("OutletActivity", "onResponse called");
//                if (response.isSuccessful()) {
//                    List<MenuResponse> menuData = response.body();
//                    menuAdapter.updateData(menuData);
//                    Log.d("menudata", "menuData: " + menuData);
//                } else {
//                    // Handle unsuccessful response
//                    Log.e("OutletActivity", "Failed to fetch menu data: " + response.message());
//                    try {
//                        if (response.errorBody() != null) {
//                            Log.e("OutletActivity", "Error response: " + response.errorBody().string());
//                        } else {
//                            Log.e("OutletActivity", "Error response body is null");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<MenuResponse>> call, Throwable t) {
//                // Handle network call failure
//                Log.e("OutletActivity", "Network call failed: " + t.getMessage());
//            }
//        });
//    }
//
//    @Override
//    public void onMinusButtonClick(MenuResponse menu) {
//        // Decrease the quantity when the '-' button is clicked
//        int currentQuantity = menu.getQuantity();
//        if (currentQuantity > 0) {
//            menu.setQuantity(currentQuantity - 1);
//        }
//        // Notify the adapter that the data has changed
//        menuAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onPlusButtonClick(MenuResponse menu) {
//        // Increase the quantity when the '+' button is clicked
//        int currentQuantity = menu.getQuantity();
//        menu.setQuantity(currentQuantity + 1);
//        // Notify the adapter that the data has changed
//        menuAdapter.notifyDataSetChanged();
//    }
//
//}

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.fragment.OrderFragment;
import com.jungkatjungkit.ecanteen.fragment.OutletFragment;

public class OutletActivity extends AppCompatActivity{

    private List<MenuResponse> quantityData = new ArrayList<>();
    Button btnOrder,btnBack;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        Intent intent = getIntent();
        btnOrder = findViewById(R.id.orderBtn);
//        btnBack = findViewById(R.id.btnBack);
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent Back = new Intent(OutletActivity.this, MainActivity.class);
//                startActivity(Back);
//            }
//        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The existing intent from the outer scope is accessible here
                if (getIntent() != null && getIntent().hasExtra("OUTLET_ID") && getIntent().hasExtra("OUTLET_NAME")) {
                    int outletId = getIntent().getIntExtra("OUTLET_ID", -1);
                    String outletName = getIntent().getStringExtra("OUTLET_NAME");

                    // Create an instance of OrderFragment with the necessary data
                    OrderFragment orderFragment = OrderFragment.newInstance(outletId, outletName);

                    // Check if the fragment is added to the activity
                    if (orderFragment.isAdded()) {
                        // Remove the fragment from the container before adding a new one
                        getSupportFragmentManager().beginTransaction()
                                .remove(orderFragment)
                                .commit();
                    }

                    // Replace the fragmentContainer with OrderFragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, orderFragment)
                            .commit();
                }
                btnOrder.setVisibility(View.GONE);
            }
        });



// The rest of your code (outside the OnClickListener)
        if (intent != null && intent.hasExtra("OUTLET_ID") && intent.hasExtra("OUTLET_NAME")) {
            int outletId = intent.getIntExtra("OUTLET_ID", -1);
            String outletName = intent.getStringExtra("OUTLET_NAME");

            // Create an instance of OutletFragment with the necessary data
            OutletFragment outletFragment = OutletFragment.newInstance(outletId, outletName);

            // Replace the fragmentContainer with OutletFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, outletFragment)
                    .commit();
        }


    }
    public interface OnOrderClickListener {
        void onOrderClick(List<MenuResponse> selectedMenuItems);
    }

    public void updateQuantity(MenuResponse menu) {
        // Check if the menu is already in the list
        if (quantityData.contains(menu)) {
            // Update the quantity
            int index = quantityData.indexOf(menu);
            quantityData.set(index, menu);
        } else {
            // Add the menu to the list if not present
            quantityData.add(menu);
        }
    }
    public List<MenuResponse> getQuantityData() {
        return quantityData;
    }
}
