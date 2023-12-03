package com.jungkatjungkit.ecanteen;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jungkatjungkit.ecanteen.config.user.ApiResponse;
import com.jungkatjungkit.ecanteen.config.user.ApiService;
import com.jungkatjungkit.ecanteen.config.user.LoginRequest;
import com.jungkatjungkit.ecanteen.config.apiURL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnLupaPassword, btnRegister;
    private EditText femail, fpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        femail = findViewById(R.id.editTextTextEmailAddress);
        fpassword = findViewById(R.id.editTextTextPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> {
            Intent mvRegister = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(mvRegister);
        });

        btnLogin.setOnClickListener(view -> {
            // Pindahkan pengambilan nilai email dan password ke dalam onClick
            String email = femail.getText().toString();
            String password = fpassword.getText().toString();

            // Cek apakah email dan password sudah diisi
            if (email.isEmpty() || password.isEmpty()) {
                // Tampilkan pesan kesalahan jika belum diisi
                Toast.makeText(LoginActivity.this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show();
                return; // Keluar dari onClick jika ada yang belum diisi
            }

            // Buat objek LoginRequest
            LoginRequest loginRequest = new LoginRequest(email, password);

            // Inisialisasi Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiURL.url()+"user/") // Ganti dengan URL API Anda
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Inisialisasi ApiService
            ApiService apiService = retrofit.create(ApiService.class);

            // Panggil metode login dari ApiService
            Call<ApiResponse> call = apiService.login(loginRequest);

            // Proses permintaan
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        // Tanggapan sukses
                        ApiResponse apiResponse = response.body();
                        String message = apiResponse.getMessage();

                        if (message != null && message.equals("login success")) {
                            // Login berhasil, buka MainActivity
                            Intent mvMain = new Intent(LoginActivity.this, MainActivity.class);
                            mvMain.putExtra("KEY_EMAIL", email);
                            finish(); // Sebaiknya tutup LoginActivity setelah login berhasil
                            startActivity(mvMain);
                        } else {
                            // Login gagal, tampilkan pesan kesalahan
                            Toast.makeText(LoginActivity.this, "Login gagal, cek email dan password", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // Tanggapan tidak sukses
                        Log.e("LoginActivity", "Error: " + response.message());
                        // Tampilkan pesan kesalahan jika respons tidak sukses
                        Toast.makeText(LoginActivity.this, "Terjadi kesalahan saat login", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    // Gagal melakukan permintaan
                    Log.e("LoginActivity", "Failed to make API call", t);
                    // Tampilkan pesan kesalahan jika gagal melakukan permintaan
                    Toast.makeText(LoginActivity.this, "Gagal melakukan login. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
