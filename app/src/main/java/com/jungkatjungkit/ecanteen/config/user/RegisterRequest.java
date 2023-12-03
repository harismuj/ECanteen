package com.jungkatjungkit.ecanteen.config.user;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("nama")
    private String nama;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public RegisterRequest(String nama, String email, String password) {
        this.nama = nama;
        this.email = email;
        this.password = password;
    }
}
