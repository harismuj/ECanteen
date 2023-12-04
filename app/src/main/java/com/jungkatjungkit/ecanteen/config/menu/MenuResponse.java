package com.jungkatjungkit.ecanteen.config.menu;

import com.google.gson.annotations.SerializedName;

public class MenuResponse {

    @SerializedName("foto")
    private String foto;
    @SerializedName("harga")
    private int harga;
    @SerializedName("kategori_id")
    private int kategoriId;
    @SerializedName("menu_id")
    private int menuId;
    @SerializedName("nama_menu")
    private int namaMenu;
    @SerializedName("outlet_id")
    private int outletId;

    public String getFoto() {
        return foto;
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

    public int getNamaMenu() {
        return namaMenu;
    }

    public int getOutletId() {
        return outletId;
    }
}
