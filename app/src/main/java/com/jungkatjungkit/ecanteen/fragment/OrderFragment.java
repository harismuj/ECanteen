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
import android.widget.TextView;

import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.activity.OutletActivity;
import com.jungkatjungkit.ecanteen.config.menu.MenuAdapter;
import com.jungkatjungkit.ecanteen.config.menu.MenuResponse;
import com.jungkatjungkit.ecanteen.config.order.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    private TextView outletNameTextView;
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
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

        // Call the method to access quantity data
        accessQuantityData();

        Bundle args = getArguments();
        Intent intent = getActivity().getIntent();
        if (args != null && args.containsKey("OUTLET_NAME")) {
            int outletId = intent.getIntExtra("OUTLET_ID", -1);
            String outletName = args.getString("OUTLET_NAME");
            outletNameTextView.setText(outletName);

            // Fetch menu data for the specific outlet (replace -1 with actual outlet ID)
//            fetchMenuData(outletId);
        }
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

}