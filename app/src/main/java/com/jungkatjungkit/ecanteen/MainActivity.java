package com.jungkatjungkit.ecanteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.navigation_home) {
                // Dalam sebuah aktivitas
                Intent intent = getIntent();
                String value = intent.getStringExtra("KEY_EMAIL");

                HomeFragment fragment = new HomeFragment();
                // Membuat Bundle dan menaruh data di dalamnya
                Bundle bundle = new Bundle();
                bundle.putString("KEY_EMAIL", value);
                fragment.setArguments(bundle);

                // Kirim data ke HomeFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            } else if (item.getItemId() == R.id.navigation_dashboard) {
                // Ganti dengan fragment dashboard jika diperlukan

            } else if (item.getItemId() == R.id.navigation_profile) {
                // Ganti dengan fragment profile jika diperlukan

            } else {
                return false; // Mengembalikan false jika item tidak dikenali
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

            return true;
        });




        // Set default fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }
}
