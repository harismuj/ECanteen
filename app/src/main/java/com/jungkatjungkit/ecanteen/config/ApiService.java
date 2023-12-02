package com.jungkatjungkit.ecanteen.config;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<ApiResponse> login(@Body LoginRequest loginRequest);
}
