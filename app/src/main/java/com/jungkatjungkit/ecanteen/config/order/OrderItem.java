package com.jungkatjungkit.ecanteen.config.order;

public class OrderItem {
    private int id;
    private int item;
    private int total_harga;

    public OrderItem(int id, int item, int total_harga) {
        this.id = id;
        this.item = item;
        this.total_harga = total_harga;
    }
}
