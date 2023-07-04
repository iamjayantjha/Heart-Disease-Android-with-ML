package com.heartdiseasedetection.application;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeartData {

    @SerializedName("heart_disease")
    @Expose
    private String heartDisease;

    public String getHeartDisease() {
        return heartDisease;
    }

    public void setHeartDisease(String heartDisease) {
        this.heartDisease = heartDisease;
    }

}
