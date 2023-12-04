package com.jungkatjungkit.ecanteen.config.menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MenuApiService {

    @GET("api/menu/outlet/{outlet_id}")
    Call<List<MenuResponse>> getMenu(
            @Path("outlet_id") int outletId
    );
}
