package com.example.finalproject;

import com.google.gson.annotations.SerializedName;

public class ExchangeRate {
    @SerializedName("cur_unit")
    private String currencyUnit;

    @SerializedName("ttb")
    private String buyingRate;

    @SerializedName("tts")
    private String sellingRate;

    @SerializedName("deal_bas_r")
    private String dealBasisRate;

    @SerializedName("cur_nm")
    private String currencyName;

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public String getBuyingRate() {
        return buyingRate;
    }

    public String getSellingRate() {
        return sellingRate;
    }

    public String getDealBasisRate() {
        return dealBasisRate;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
