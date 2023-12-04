package com.jungkatjungkit.ecanteen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jungkatjungkit.ecanteen.fragment.HomeFragment;
import com.jungkatjungkit.ecanteen.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            Intent intent = getIntent();
            if (item.getItemId() == R.id.navigation_home) {
                // Menerima data dari LoginActivity

                String value = intent.getStringExtra("KEY_EMAIL");

                // Cek apakah sudah berada di HomeFragment
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                if (currentFragment == null || !(currentFragment instanceof HomeFragment)) {
                    // Jika belum berada di HomeFragment, lakukan transaksi
                    HomeFragment fragment = new HomeFragment();

                    // Membuat Bundle dan menaruh data di dalamnya
                    Bundle bundle = new Bundle();
                    bundle.putString("KEY_EMAIL", value);
                    fragment.setArguments(bundle);

                    // Kirim data ke HomeFragment
                    selectedFragment = fragment;
                } else {
                    // Jika sudah berada di HomeFragment, tidak perlu lakukan apa-apa
                    // atau bisa menambahkan log atau pesan jika diperlukan
                    // Log.d("MainActivity", "Sudah berada di HomeFragment");
                }

            } else if (item.getItemId() == R.id.navigation_order) {
                // Ganti dengan fragment order jika diperlukan
                // selectedFragment = new OrderFragment();

            } else if (item.getItemId() == R.id.navigation_profile) {
                // Menerima data dari LoginActivity
                String value = intent.getStringExtra("KEY_EMAIL");

                // Cek apakah sudah berada di HomeFragment
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                if (currentFragment == null || !(currentFragment instanceof ProfileFragment)) {
                    // Jika belum berada di HomeFragment, lakukan transaksi
                    ProfileFragment fragment = new ProfileFragment();

                    // Membuat Bundle dan menaruh data di dalamnya
                    Bundle bundle = new Bundle();
                    bundle.putString("KEY_EMAIL", value);
                    fragment.setArguments(bundle);

                    // Kirim data ke HomeFragment
                    selectedFragment = fragment;
                } else {
                    // Jika sudah berada di HomeFragment, tidak perlu lakukan apa-apa
                    // atau bisa menambahkan log atau pesan jika diperlukan
                    // Log.d("MainActivity", "Sudah berada di HomeFragment");
                }

            } else {
                return false; // Mengembalikan false jika item tidak dikenali
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }

            return true;
        });

        // Set default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }
}
