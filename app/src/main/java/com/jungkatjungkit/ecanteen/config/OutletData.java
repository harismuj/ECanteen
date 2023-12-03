package com.jungkatjungkit.ecanteen.config;

public class OutletData {
    private int outlet_id;
    private String nama_outlet;
    private int jumlah_menu;
    private String foto;

    public OutletData() {
        // Empty constructor required by Retrofit
    }

    public OutletData(int outlet_id, String nama_outlet, int jumlah_menu, String foto) {
        this.outlet_id = outlet_id;
        this.nama_outlet = nama_outlet;
        this.jumlah_menu = jumlah_menu;
        this.foto = foto;
    }

    public int getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(int outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getNama_outlet() {
        return nama_outlet;
    }

    public void setNama_outlet(String nama_outlet) {
        this.nama_outlet = nama_outlet;
    }

    public int getJumlah_menu() {
        return jumlah_menu;
    }

    public void setJumlah_menu(int jumlah_menu) {
        this.jumlah_menu = jumlah_menu;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
