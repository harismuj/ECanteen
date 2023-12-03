package com.jungkatjungkit.ecanteen.config.pesanan;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static final String BASE_URL = "http://101.50.2.14:13000/";

    private static Retrofit retrofit = null;

    public static OrderApiService getOrderApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(OrderApiService.class);
    }
}
