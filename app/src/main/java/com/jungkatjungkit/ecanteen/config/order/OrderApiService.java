package com.jungkatjungkit.ecanteen.config.order;

import com.jungkatjungkit.ecanteen.config.riwayat.OrderResponse;
import com.jungkatjungkit.ecanteen.config.user.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderApiService {
    @POST("outlet/order") // Replace with your actual endpoint
    Call<ResponseOrder> postOrder(@Body OrderRequest orderRequest);
}
