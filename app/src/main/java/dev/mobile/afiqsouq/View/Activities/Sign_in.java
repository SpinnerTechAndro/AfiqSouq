package dev.mobile.afiqsouq.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.textfield.TextInputEditText;

import dev.mobile.afiqsouq.Models.LoginResp;
import dev.mobile.afiqsouq.Models.Recived_Sign_up;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.View.Home_Activity;
import dev.mobile.afiqsouq.api.api;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_in extends AppCompatActivity {
    TextInputEditText email, pass;
    String emailSt, passSt;
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // retrofit client
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        spinKitView = findViewById(R.id.spin_kitL);


        findViewById(R.id.button_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailSt = email.getText().toString();
                passSt = pass.getText().toString();

                if (TextUtils.isEmpty(emailSt) || TextUtils.isEmpty(passSt)) {
                    Toasty.error(getApplicationContext(), "Please Fill The Data Properly !", 1)
                            .show();
                } else {
                    login();
                }
            }
        });

        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), SignUP.class);
                startActivity(p);
            }
        });

        // login() ;

        findViewById(R.id.forget_password_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent p = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(p);

            }
        });

    }

    private void login() {
        spinKitView.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api apiInterface = retrofit.create(api.class);
//Rahatshovo  , 1234567856
        Call<LoginResp> call = apiInterface.Login(emailSt, passSt);

        call.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                if (response.code() == 200) {
                    try {
                        LoginResp model = response.body();
                        LoginResp.User user = model.getUser();
                        //  Log.d("TAG", "onResponse: "+ user.getDisplayname() + user.getEmail());
                        //save user here
                        //String id, String name, String email, String adress, String country, String ph, String state
//                        SharedPrefManager.getInstance(getApplicationContext())
//                                .userLogin(
//                                        user.getId(),
//                                        user.getUsername(),
//                                        user.getEmail()
//
//                                );
                        if (user != null) {
                            requestForDetails(user.getId());

                        } else {
                            spinKitView.setVisibility(View.GONE);
                            Toasty.error(getApplicationContext(), "Your Account Could not Be Found !!", Toasty.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        spinKitView.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Password Or Username is Wrong", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    spinKitView.setVisibility(View.GONE);
                    Log.d("TAG", "onFailure: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void requestForDetails(int id) {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        Call<Recived_Sign_up> signUpRespCall = RetrofitClient
                .getInstance()
                .getApi()
                .getSingleData(authHeader, id);

        signUpRespCall.enqueue(new Callback<Recived_Sign_up>() {
            @Override
            public void onResponse(Call<Recived_Sign_up> call, Response<Recived_Sign_up> response) {
                if (response.code() == 200) {
                    Recived_Sign_up userModel = response.body();
                    try {
                        if (userModel != null) {

                            // save user
                            //String id, String name, String email, String adress, String country, String ph, String state)
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .userLogin(
                                            userModel.getId() + "",
                                            userModel.getUsername(),
                                            userModel.getEmail(),
                                            userModel.getShipping().getAddress1(),
                                            userModel.getShipping().getCountry(),
                                            userModel.getBilling().getPhone(),
                                            userModel.getShipping().getState()

                                    );
                            SharedPrefManager.getInstance(getApplicationContext()).saveUser(userModel.getUsername());

                            // now go to home
                            // go to home

                            gotToHome();

                        } else {
                            spinKitView.setVisibility(View.GONE);
                            Toasty.error(getApplicationContext(), "Error : User Data Did NOt Be Found ", Toasty.LENGTH_SHORT).show();

                        }
                    } catch (Exception r) {
                        spinKitView.setVisibility(View.GONE);
                        Toasty.error(getApplicationContext(), "Error : " + r.getMessage(), Toasty.LENGTH_SHORT).show();

                    }
                } else {
                    spinKitView.setVisibility(View.GONE);
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), Toasty.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Recived_Sign_up> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });


    }

    private void gotToHome() {
        spinKitView.setVisibility(View.GONE);
        Intent p = new Intent(getApplicationContext(), Home_Activity.class);
        startActivity(p);
        finish();
    }
}