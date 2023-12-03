package com.jungkatjungkit.ecanteen.config;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("login")
    Call<ApiResponse> login(@Body LoginRequest loginRequest);

    @POST("register")
    Call<ApiResponse> register(@Body RegisterRequest registerRequest);

    @GET("getuser/{email}")
    Call<ApiResponse> getUserByEmail(@Path("email") String email);
}
