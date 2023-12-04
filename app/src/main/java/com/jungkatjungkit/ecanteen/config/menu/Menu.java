package com.jungkatjungkit.ecanteen.config.menu;

import com.jungkatjungkit.ecanteen.config.apiURL;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Menu {

    public static final String BASE_URL = "http://101.50.2.14:13000/";

    public static Retrofit retrofit = null;

    public static MenuApiService getMenuApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .build())
                    .build();
        }

        return retrofit.create(MenuApiService.class);
    }
}
