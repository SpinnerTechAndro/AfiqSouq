package dev.mobile.afiqsouq.View.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Random;

import dev.mobile.afiqsouq.Models.Recived_Sign_up;
import dev.mobile.afiqsouq.Models.ResetPassword;
import dev.mobile.afiqsouq.Models.SignUpResp;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.View.Home_Activity;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class RecoverOtp extends AppCompatActivity {
    PinView pinView;
    MaterialButton verifyBtn;
    Recived_Sign_up user_model;
    String ph_num;
    SpinKitView spinKitView;
    String verification_code = "000";
    int IS_RECOVER = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_otp);
        pinView = findViewById(R.id.pinView_verificationn_OTP);
        verifyBtn = findViewById(R.id.button_verification_verify);
        spinKitView = findViewById(R.id.spin_kit);
        spinKitView.setVisibility(View.GONE);

        user_model = (Recived_Sign_up) getIntent().getSerializableExtra("MODELS");

        if (user_model != null) {
            ph_num = user_model.getBilling().getPhone();
            sendOtp(ph_num);
        }

        findViewById(R.id.imageview_verification_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredPin = "000" ;
                try{
                    enteredPin = pinView.getText().toString() ;
                }
                catch (Exception r ){
                    Toasty.error(getApplicationContext() , "Try Again Some Error With Otp !!" , 1).show();
                }

                //Toasty.success(getApplicationContext() , " " + enteredPin , 1).show();
                if(verification_code.equals(enteredPin)){
                    changePass(user_model);
                }
                else {
                    Toasty.error(getApplicationContext() , "Wrong Pin" , 1).show();
                }
            }
        });

    }

    private void sendOtp(String ph_num) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        verification_code = getRandomNumberString();
                        String msg = "Welcome To Afiq Souq!!. Your Recover Code is :  ";
                        msg += verification_code;

                        // seend the otp to the user
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                        RequestBody body = RequestBody.create(mediaType, "");
                        Request request = new Request.Builder()
                                .url("http://66.45.237.70/api.php?username=afiqsouq2021&password=12afiqsouq3434$&number=88"+ ph_num+"&message=" + msg)
                                .method("POST", body)
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .build();
                        Response response = client.newCall(request).execute();
                    }

                    catch (Throwable t ) {
                        Toast.makeText(getApplicationContext(), t.getMessage() + " " ,Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


    }





    public void changePass(Recived_Sign_up user_model) {  // register here
        final Dialog couponChecker = new Dialog(RecoverOtp.this);
        couponChecker.requestWindowFeature(Window.FEATURE_NO_TITLE);
        couponChecker.setContentView(R.layout.reset_password);
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        final TextInputEditText coupon = (TextInputEditText) couponChecker.findViewById(R.id.pass);
        final  MaterialButton button = couponChecker.findViewById(R.id.reset1) ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = coupon.getText().toString() ;

                if(!TextUtils.isEmpty(newPass)){
                    spinKitView.setVisibility(View.VISIBLE);
                    ResetPassword resetPassword = new ResetPassword(newPass) ;
                    // send this
                    Call<Recived_Sign_up> restCall = RetrofitClient.getInstance()
                            .getApi()
                            .resetPass(authHeader , resetPassword , user_model.getId()) ;

                    restCall.enqueue(new Callback<Recived_Sign_up>() {
                        @Override
                        public void onResponse(Call<Recived_Sign_up> call, retrofit2.Response<Recived_Sign_up> response) {
                            if(response.code()== 200){
                                spinKitView.setVisibility(View.GONE);
                                couponChecker.dismiss();
                                Intent p = new Intent(getApplicationContext() , Sign_in.class);
                                p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(p);
                                finish();


                            }
                            else {
                                spinKitView.setVisibility(View.GONE);
                                Toasty.error(getApplicationContext(), "Error :  Something went wrong", 1 ).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Recived_Sign_up> call, Throwable t) {
                            spinKitView.setVisibility(View.GONE);
                            Toasty.error(getApplicationContext(), "Error :  " + t.getMessage() , 1 ).show();

                        }
                    });


                }else {
                    Toasty.error(getApplicationContext(), "Error : Add New Password " , 1 ).show();
                }
            }
        });
        //spinKitView.setVisibility(View.VISIBLE);
         // trigger dialgoue

            couponChecker.show();


    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}