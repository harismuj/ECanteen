package com.jungkatjungkit.ecanteen;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jungkatjungkit.ecanteen.config.ApiResponse;
import com.jungkatjungkit.ecanteen.config.ApiService;
import com.jungkatjungkit.ecanteen.config.LoginRequest;
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

        btnLogin.setOnClickListener(view -> {
            String email = femail.getText().toString();
            String password = fpassword.getText().toString();

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
                        Log.d("LoginActivity", "Message: " + message);

                        // Handle sesuai kebutuhan Anda
                    } else {
                        // Tanggapan tidak sukses
                        Log.e("LoginActivity", "Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    // Gagal melakukan permintaan
                    Log.e("LoginActivity", "Failed to make API call", t);
                }
            });
        });
    }
}
