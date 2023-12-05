package com.jungkatjungkit.ecanteen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.fragment.HistoryFragment;
import com.jungkatjungkit.ecanteen.fragment.OngoingFragment;

public class PesananFragment extends Fragment {
    Button btnOngoing, btnHistory;

    public PesananFragment() {
        // Required empty public constructor
    }

    public static PesananFragment newInstance(String param1, String param2) {
        PesananFragment fragment = new PesananFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesanan, container, false);

        // Mendapatkan referensi ke tombol btnOngoing dan btnHistory
        btnOngoing = view.findViewById(R.id.btnOngoing);
        btnHistory = view.findViewById(R.id.btnHistory);
        openFragment(new OngoingFragment());
        // Menambahkan listener untuk tombol btnOngoing
        btnOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(new OngoingFragment());
            }
        });

        // Menambahkan listener untuk tombol btnHistory
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(new HistoryFragment());
            }
        });

        return view;
    }

    private void openFragment(Fragment fragment) {
        // Membuat transaksi fragment
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();

        // Mengganti fragment yang sedang ditampilkan dengan fragment baru
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);

        // Menambahkan transaksi ke tumpukan dan menyelesaikan transaksi
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
