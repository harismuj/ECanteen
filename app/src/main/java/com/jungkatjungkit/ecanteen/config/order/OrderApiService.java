package com.jungkatjungkit.ecanteen.config.order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderApiService {
    @POST("outlet/order") // Replace with your actual endpoint
    Call<Void> postOrder(@Body OrderRequest orderRequest);
}
