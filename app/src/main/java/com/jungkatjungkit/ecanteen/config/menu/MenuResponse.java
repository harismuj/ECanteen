package com.jungkatjungkit.ecanteen.config.menu;

import com.google.gson.annotations.SerializedName;

public class MenuResponse {

    @SerializedName("menu_foto")
    private String menu_foto;
    @SerializedName("harga")
    private int harga;
    @SerializedName("kategori_id")
    private int kategoriId;
    @SerializedName("menu_id")
    private int menuId;
    @SerializedName("nama_menu")
    private String namaMenu;
    @SerializedName("nama_kategori")
    private String namaKategori;
    @SerializedName("outlet_id")
    private int outletId;

    public String getMenu_foto() {
        return menu_foto;
    }

    public int getHarga() {
        return harga;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getNamaMenu() {
        return namaMenu;
    }
    public String getNamaKategori() {
        return namaKategori;
    }

    public int getOutletId() {
        return outletId;
    }
}
