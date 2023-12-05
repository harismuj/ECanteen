package com.jungkatjungkit.ecanteen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.config.riwayat.Client;
import com.jungkatjungkit.ecanteen.config.riwayat.OngoingOrderAdapter;
import com.jungkatjungkit.ecanteen.config.riwayat.OrderApiService;
import com.jungkatjungkit.ecanteen.config.riwayat.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OngoingFragment extends Fragment {

    private RecyclerView recyclerView;
    private OngoingOrderAdapter adapter;

    public OngoingFragment() {
        // Required empty public constructor
    }

    public static OngoingFragment newInstance() {
        return new OngoingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_going, container, false);

        recyclerView = view.findViewById(R.id.onGoingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new OngoingOrderAdapter();
        recyclerView.setAdapter(adapter);

        // Mendapatkan data dari Intent
        Intent intent = getActivity().getIntent();
        String email = intent.getStringExtra("KEY_EMAIL");

        fetchLatestOrders(email);

        return view;
    }

    private void fetchLatestOrders(String email) {
        OrderApiService orderApiService = Client.getOrderApiService();
        Call<List<OrderResponse>> call = orderApiService.getLatestOrderByStatus(email,0);
        call.enqueue(new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<OrderResponse> orderResponses = response.body();
                    adapter.setOrderList(orderResponses);
                } else {
                    // Handle unsuccessful response or empty list
                    Log.e("OngoingFragment", "Response not successful or empty");
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                // Handle failure here (e.g., log an error)
                Log.e("OngoingFragment", "Request failed: " + t.getMessage());
            }
        });
    }
}
