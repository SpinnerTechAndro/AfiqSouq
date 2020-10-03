package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Models.TaxREsp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.View.Home_Activity;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences shref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // request for tax list
        loadTax();
    }

    private void loadTax() {

        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        Call<List<TaxREsp>> listCall = RetrofitClient.getInstance()
                .getApi()
                .getAllTax(authHeader);

        listCall.enqueue(new Callback<List<TaxREsp>>() {
            @Override
            public void onResponse(Call<List<TaxREsp>> call, Response<List<TaxREsp>> response) {
                if (response.code() == 200) {
                    // noiw save the tax list
                    String key = "TAX_KEY";
                    List<TaxREsp> ModelArrayList=new ArrayList<>();

                    ModelArrayList = response.body() ;
                    shref = getSharedPreferences("TAX", MODE_PRIVATE);

                    Gson gson = new Gson();
                    String json = gson.toJson(ModelArrayList);

                    editor = shref.edit();
                    editor.putString(key, json);
                    editor.apply();
                    Toasty.normal(getApplicationContext() , ModelArrayList.size()+" " , Toasty.LENGTH_LONG).show();

                    Intent p = new Intent(getApplicationContext() , Home_Activity.class);
                    startActivity(p);
                    finish();

                } else {
                    loadTax();
                }
            }

            @Override
            public void onFailure(Call<List<TaxREsp>> call, Throwable t) {
                loadTax();
            }
        });
    }
}