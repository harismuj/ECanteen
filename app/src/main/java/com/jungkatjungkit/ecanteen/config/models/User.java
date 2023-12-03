package com.jungkatjungkit.ecanteen.config.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("email")
    private String email;

    @SerializedName("nama")
    private String nama;

    @SerializedName("password")
    private String password;

    @SerializedName("picture")
    private String picture;

    // Constructors
    public User() {
    }

    public User(String email, String nama, String password, String picture) {
        this.email = email;
        this.nama = nama;
        this.password = password;
        this.picture = picture;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
