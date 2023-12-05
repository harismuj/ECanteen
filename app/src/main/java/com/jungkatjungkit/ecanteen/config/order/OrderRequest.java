package com.jungkatjungkit.ecanteen.config.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequest {
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("catatan")
    private String catatan;
    @SerializedName("pesanan")
    private String pesanan;
    @SerializedName("total")
    private int total;

    public OrderRequest(int user_id, String catatan, String pesanan, int total) {
        this.user_id = user_id;
        this.catatan = catatan;
        this.pesanan = pesanan;
        this.total = total;
    }
}
