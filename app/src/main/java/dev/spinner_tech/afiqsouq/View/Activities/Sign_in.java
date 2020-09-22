package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import dev.spinner_tech.afiqsouq.Models.LoginResp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.api.api;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // retrofit client

        login() ;

    }

    private void login() {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(Constants.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api apiInterface = retrofit.create(api.class);

        Call<LoginResp> call = apiInterface.Login("Rahatshovo" , "1234567856") ;

        call.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                if(response.code() == 200){
                    try{
                        LoginResp model = response.body() ;
                        LoginResp.User user  = model.getUser() ;
                        Log.d("TAG", "onResponse: "+ user.getDisplayname() + user.getEmail());
                    }
                    catch (Exception e ){
                        Toast.makeText(getApplicationContext() , "Password Or Username is Wrong" , Toast.LENGTH_LONG)
                                .show();
                    }
                }
                else {
                    Log.d("TAG", "onFailure: " +response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}