package com.jungkatjungkit.ecanteen.config.order;

import com.google.gson.annotations.SerializedName;

public class ResponseOrder {

    @SerializedName("result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

