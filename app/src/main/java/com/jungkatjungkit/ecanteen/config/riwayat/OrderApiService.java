package com.jungkatjungkit.ecanteen.config.riwayat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderApiService {

    @GET("api/order/latest/{email}/{limit}")
    Call<List<OrderResponse>> getLatestOrders(
            @Path("email") String email,
            @Path("limit") int limit
    );
    @GET("api/order/latest/{email}/s/{status}")
    Call<List<OrderResponse>> getLatestOrderByStatus(
            @Path("email") String email,
            @Path("status") int limit
    );
}
