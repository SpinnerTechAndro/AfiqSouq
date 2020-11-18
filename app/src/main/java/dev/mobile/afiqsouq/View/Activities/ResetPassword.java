package dev.mobile.afiqsouq.View.Activities;

import android.content.Intent;
import android.os.Bundle;
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
import dev.mobile.afiqsouq.api.api;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPassword extends AppCompatActivity {

    TextInputEditText newPass, confirmNewPass, oldPass;
    String new_pass, confirm_pass, old_pass;
    SpinKitView spinKitView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newPass = findViewById(R.id.newpass);
        confirmNewPass = findViewById(R.id.newPassConfirm);
        oldPass = findViewById(R.id.oldPass);
        spinKitView = findViewById(R.id.spin_kitL);

        findViewById(R.id.button_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass = newPass.getText().toString();
                confirm_pass = confirmNewPass.getText().toString();
                old_pass = oldPass.getText().toString();
                // Lets check if the the current password is OK
                if (confirm_pass.equals(new_pass)) {
                    login(old_pass, confirm_pass, new_pass);
                } else {
                    Toasty.error(getApplicationContext(), "Your New Password Don't Match !!", Toasty.LENGTH_SHORT).show();

                }


            }
        });


        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void login(String old_pass1, String confirm_pass1, String new_pass1) {
        spinKitView.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api apiInterface = retrofit.create(api.class);
        //Rahatshovo  , 1234567856
        Call<LoginResp> call = apiInterface.Login(SharedPrefManager.getInstance(getApplicationContext()).getUser().getMail(), old_pass1);

        call.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                if (response.code() == 200) {
                    try {
                        LoginResp model = response.body();
                        LoginResp.User user = model.getUser();
                        if (user != null) {
                            // now reset the password
                            changePass(user.getId(), confirm_pass1);

                        } else {
                            spinKitView.setVisibility(View.GONE);
                            Toasty.error(getApplicationContext(), "Your Current Password is Wrong !!", Toasty.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        spinKitView.setVisibility(View.GONE);
                        Toasty.error(getApplicationContext(), "Your Current Password is Wrong !!", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    spinKitView.setVisibility(View.GONE);
                    Log.d("TAG", "onFailure: " + response.code());
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), Toast.LENGTH_LONG)
                        .show();
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void changePass(int sId, String confirm_pass1) {  // register here
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        dev.mobile.afiqsouq.Models.ResetPassword resetPassword = new dev.mobile.afiqsouq.Models.ResetPassword(confirm_pass1);
        // send this
        Call<Recived_Sign_up> restCall = RetrofitClient.getInstance()
                .getApi()
                .resetPass(authHeader, resetPassword, sId);

        restCall.enqueue(new Callback<Recived_Sign_up>() {
            @Override
            public void onResponse(Call<Recived_Sign_up> call, retrofit2.Response<Recived_Sign_up> response) {
                if (response.code() == 200) {
                    spinKitView.setVisibility(View.GONE);
                    Toasty.success(getApplicationContext(), "Success :  Your Password CHanged Please Login Again ...", 2).show();
                    Intent p = new Intent(getApplicationContext(), Sign_in.class);
                    p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(p);
                    finish();


                } else {
                    spinKitView.setVisibility(View.GONE);
                    Toasty.error(getApplicationContext(), "Error :  Something went wrong", 1).show();

                }
            }

            @Override
            public void onFailure(Call<Recived_Sign_up> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
                Toasty.error(getApplicationContext(), "Error :  " + t.getMessage(), 1).show();

            }
        });


    }
}
