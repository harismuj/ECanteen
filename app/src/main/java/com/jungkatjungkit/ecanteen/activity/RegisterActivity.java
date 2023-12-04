package com.jungkatjungkit.ecanteen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jungkatjungkit.ecanteen.MainActivity;
import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.config.user.ApiResponse;
import com.jungkatjungkit.ecanteen.config.user.ApiService;
import com.jungkatjungkit.ecanteen.config.user.RegisterRequest;
import com.jungkatjungkit.ecanteen.config.apiURL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etPasswordConf;
    private Button btnSignUp;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword1);
        etPasswordConf = findViewById(R.id.etPassword2);

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(view -> {
            // Pindahkan pengambilan nilai email dan password ke dalam onClick
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String password2 = etPasswordConf.getText().toString();
            String nama = etName.getText().toString();

            // Cek apakah email dan password sudah diisi
            if (email.isEmpty() || password.isEmpty()) {
                // Tampilkan pesan kesalahan jika belum diisi
                Toast.makeText(RegisterActivity.this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show();
                return; // Keluar dari onClick jika ada yang belum diisi
            } if(!password.equals(password2)){
                Toast.makeText(RegisterActivity.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buat objek LoginRequest
            RegisterRequest registerRequest = new RegisterRequest(nama, email, password);

            // Inisialisasi Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiURL.url()+"user/") // Ganti dengan URL API Anda
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Inisialisasi ApiService
            ApiService apiService = retrofit.create(ApiService.class);

            // Panggil metode login dari ApiService
            Call<ApiResponse> call = apiService.register(registerRequest);

            // Proses permintaan
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        ApiResponse apiResponse = response.body();
                        String result = apiResponse.getResult();

                        if (result != null && result.equals("Registration successful. You can now log in.")) {
                            // Registration berhasil, buka MainActivity
                            Intent mvMain = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(mvMain);
                            finish(); // Sebaiknya tutup RegisterActivity setelah registrasi berhasil
                        } else {
                            // Registrasi gagal, tampilkan pesan kesalahan
                            Toast.makeText(RegisterActivity.this, "Registrasi gagal, cek data Anda", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Tanggapan tidak sukses
                        Log.e("RegisterActivity", "Error: " + response.message());
                        // Tampilkan pesan kesalahan jika respons tidak sukses
                        Toast.makeText(RegisterActivity.this, "Terjadi kesalahan saat registrasi", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    // Gagal melakukan permintaan
                    Log.e("RegisterActivity", "Failed to make API call", t);
                    // Tampilkan pesan kesalahan jika gagal melakukan permintaan
                    Toast.makeText(RegisterActivity.this, "Gagal melakukan registrasi. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
                }
            });

        });

    }
}