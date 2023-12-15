package com.example.finalproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface ExchangeRateService {
    @GET("exchangeJSON")
    Call<List<ExchangeRate>> getExchangeRate(@Query("authkey") String authKey,
                                             @Query("searchdate") String searchDate,
                                             @Query("data") String data);
}


