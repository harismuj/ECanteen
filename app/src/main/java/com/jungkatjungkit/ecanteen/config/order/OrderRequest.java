package com.jungkatjungkit.ecanteen.config.order;

import java.util.List;

public class OrderRequest {
    private int user_id;
    private String catatan;
    private List<OrderItem> pesanan;
    private int total;

    public OrderRequest(int user_id, String catatan, List<OrderItem> pesanan, int total) {
        this.user_id = user_id;
        this.catatan = catatan;
        this.pesanan = pesanan;
        this.total = total;
    }
}
