package com.jungkatjungkit.ecanteen.config;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetOutlet {
    @GET("all")
    Call<OutletData> getData();
}
