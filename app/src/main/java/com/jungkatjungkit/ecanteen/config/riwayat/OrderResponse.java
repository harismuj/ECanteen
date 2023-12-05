package com.jungkatjungkit.ecanteen.config.riwayat;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {

    @SerializedName("catatan")
    private String catatan;
    @SerializedName("foto_menu")
    private String fotoMenu;
    @SerializedName("foto_outlet")
    private String fotoOutlet;
    @SerializedName("nama_outlet")
    private String namaOutlet;
    @SerializedName("tanggal_pesanan")
    private String tanggalPesanan;
    @SerializedName("nama_menu")
    private String namaMenu;
    @SerializedName("pesanan")
    private String pesanan;
    @SerializedName("total")
    private int total;

    public int getTotal() { return total; }

    public String getTanggalPesanan() {
        return tanggalPesanan;
    }

    public String pesanan() {
        return pesanan;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getNamaOutlet(){
        return namaOutlet;
    }
    public String getFotoOutlet(){
        return fotoOutlet;
    }

    public String getFotoMenu() {
        return fotoMenu;
    }


}
