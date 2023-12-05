package com.jungkatjungkit.ecanteen.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.activity.OutletActivity;
import com.jungkatjungkit.ecanteen.config.menu.Menu;
import com.jungkatjungkit.ecanteen.config.menu.MenuAdapter;
import com.jungkatjungkit.ecanteen.config.menu.MenuApiService;
import com.jungkatjungkit.ecanteen.config.menu.MenuResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutletFragment extends Fragment implements MenuAdapter.OnButtonClickListener  {
    private TextView outletNameTextView;
    private RecyclerView menuRecyclerView;
    private OutletActivity.OnOrderClickListener onOrderClickListener;
    private MenuAdapter menuAdapter;


    public static OutletFragment newInstance(int outletId, String outletName) {
        OutletFragment fragment = new OutletFragment();
        Bundle args = new Bundle();
        args.putInt("OUTLET_ID", outletId);
        args.putString("OUTLET_NAME", outletName);
        fragment.setArguments(args);
        return fragment;
    }



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outlet, container, false);

        outletNameTextView = view.findViewById(R.id.namaOutlet);
        menuRecyclerView = view.findViewById(R.id.menuRecyclerView);

        // Set up RecyclerView for the menu
        menuAdapter = new MenuAdapter(new ArrayList<>(), this);
        menuAdapter.setOnButtonClickListener(this);

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        menuRecyclerView.setAdapter(menuAdapter);

        // Retrieve outlet details from arguments
        Bundle args = getArguments();
        Intent intent = getActivity().getIntent();
        if (args != null && args.containsKey("OUTLET_NAME")) {
            int outletId = intent.getIntExtra("OUTLET_ID", -1);
            String outletName = args.getString("OUTLET_NAME");
            outletNameTextView.setText(outletName);

            // Fetch menu data for the specific outlet (replace -1 with actual outlet ID)
            fetchMenuData(outletId);
        }

        return view;
    }

    private void fetchMenuData(int outletId) {
        MenuApiService menuApiService = Menu.getMenuApiService();
        Call<List<MenuResponse>> call = menuApiService.getMenu(outletId);

        call.enqueue(new Callback<List<MenuResponse>>() {
            @Override
            public void onResponse(Call<List<MenuResponse>> call, Response<List<MenuResponse>> response) {
                Log.d("OutletActivity", "onResponse called");
                if (response.isSuccessful()) {
                    List<MenuResponse> menuData = response.body();
                    menuAdapter.updateData(menuData);
                    Log.d("menudata", "menuData: " + menuData);
                } else {
                    // Handle unsuccessful response
                    Log.e("OutletActivity", "Failed to fetch menu data: " + response.message());
                    try {
                        if (response.errorBody() != null) {
                            Log.e("OutletActivity", "Error response: " + response.errorBody().string());
                        } else {
                            Log.e("OutletActivity", "Error response body is null");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MenuResponse>> call, Throwable t) {
                // Handle network call failure
                Log.e("OutletActivity", "Network call failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void onMinusButtonClick(MenuResponse menu) {
        // Decrease the quantity when the '-' button is clicked
        int currentQuantity = menu.getQuantity();
        if (currentQuantity > 0) {
            menu.setQuantity(currentQuantity - 1);
        }
        // Notify the adapter that the data has changed
        menuAdapter.notifyDataSetChanged();

        if (getActivity() instanceof OutletActivity) {
            ((OutletActivity) getActivity()).updateQuantity(menu);
        }
    }

    @Override
    public void onPlusButtonClick(MenuResponse menu) {
        // Increase the quantity when the '+' button is clicked
        int currentQuantity = menu.getQuantity();
        menu.setQuantity(currentQuantity + 1);
        // Notify the adapter that the data has changed
        menuAdapter.notifyDataSetChanged();

        if (getActivity() instanceof OutletActivity) {
            ((OutletActivity) getActivity()).updateQuantity(menu);
        }
    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof MenuAdapter.OnButtonClickListener) {
//            // The hosting activity (OutletActivity) should implement OnButtonClickListener
//            // This ensures that the communication between fragment and activity is possible
//            menuAdapter.setOnButtonClickListener(this);
//        } else {
//            throw new ClassCastException(context.toString() + " must implement OnButtonClickListener");
//        }
//    }
}
