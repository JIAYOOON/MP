package com.example.finalproject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import java.util.Locale;
import android.widget.Toast;
import android.util.Log;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://www.koreaexim.go.kr/site/program/financial/";

    private ExchangeRateService exchangeRateService;
    private List<ExchangeRate> exchangeRates;

    private TextView textViewExchangeRate;
    private EditText editTextAmount;
    private TextView textViewConvertedAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewExchangeRate = findViewById(R.id.textViewExchangeRate);
        editTextAmount = findViewById(R.id.editTextAmount);
        textViewConvertedAmount = findViewById(R.id.textViewConvertedAmount);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.koreaexim.go.kr/site/program/financial/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        exchangeRateService = retrofit.create(ExchangeRateService.class);

        String authKey = "ODzFYGOzwjFtPNmsHOXKazaYJ8bvNjaN";
        String searchdate = "20231214";
        String data = "AP01"; // 환율 데이터 요청

        //Retrofit retrofit = RetrofitClient.getClient();
        ExchangeRateService service = retrofit.create(ExchangeRateService.class);


        Call<List<ExchangeRate>> call = service.getExchangeRate(authKey, searchdate, data);
        call.enqueue(new Callback<List<ExchangeRate>>() {
            @Override
            public void onResponse(Call<List<ExchangeRate>> call, Response<List<ExchangeRate>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exchangeRates = response.body();

                    StringBuilder ratesInfo = new StringBuilder();
                    for (ExchangeRate rate : exchangeRates) {
                        ratesInfo.append("통화 코드: ").append(rate.getCurrencyUnit()).append(", 구매환율: ").append(rate.getBuyingRate()).append("\n");
                    }

                    // TextView에 환율 정보 표시
                    textViewExchangeRate.setText(ratesInfo.toString());

                    calculateExchange();
                } else {
                    Toast.makeText(MainActivity.this, "응답 오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ExchangeRate>> call, Throwable t) {
                // API 호출 실패 시 처리
            }
        });

        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateExchange();
            }
        });
    }

    private void calculateExchange() {
        if (exchangeRates != null && !exchangeRates.isEmpty()) {
            String input = editTextAmount.getText().toString();

            if (!input.isEmpty()) {
                double amount = Double.parseDouble(input);

                // 예시로 일본 환율 정보를 사용하여 계산
                ExchangeRate firstExchangeRate = exchangeRates.get(12);
                double buyingRate = Double.parseDouble(firstExchangeRate.getBuyingRate());

                // 환율 계산
                double convertedAmount = amount * buyingRate;

                textViewConvertedAmount.setText(String.format(Locale.getDefault(), "%.2f", convertedAmount));
            } else {
                // 입력된 값이 없을 경우 처리
                Toast.makeText(this, "금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // 환율 정보를 가져오지 못한 경우 처리
            Toast.makeText(this, "환율 정보를 먼저 가져와주세요.", Toast.LENGTH_SHORT).show();
        }



    }
}

