package dev.mobile.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import dev.mobile.afiqsouq.Models.CategoryResp;
import dev.mobile.afiqsouq.Models.Recived_Sign_up;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {
    //toolbar
    ImageView back;
    //content
    EditText email;
    AppCompatButton reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //toolbar
        back = findViewById(R.id.imageview_resetpass_back);

        //enter mail and press reset button to reset password
        email = findViewById(R.id.editText_resetpass_EmailAddress);
        reset = findViewById(R.id.button_resetPass_reset);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();

                if (!TextUtils.isEmpty(mail)) {
                    // now we check for the user is exist or not
                    // then get the model with

                    searchUser(mail);


                } else {
                    Toasty.error(getApplicationContext(), "Please Enter Your Email Sir !!!", 1).show();
                    email.setError("Enter Valid Email");
                }

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void searchUser(String mail) {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<Recived_Sign_up>> call = RetrofitClient.getInstance()
                .getApi()
                .SearchUser(authHeader, mail);


        call.enqueue(new Callback<List<Recived_Sign_up>>() {
            @Override
            public void onResponse(Call<List<Recived_Sign_up>> call, Response<List<Recived_Sign_up>> response) {
                if (response.code() == 200) {
                    // check the length
                    try {
                        List<Recived_Sign_up> user_List = response.body();

                        if (user_List.size() > 0) {
                            if(user_List.size()==1){
                                Recived_Sign_up userModel = user_List.get(0) ;
                                Intent p = new Intent(getApplicationContext()  , RecoverOtp.class )  ;
                                p.putExtra("MODELS" , userModel) ;
                                startActivity(p);
                            }
                            else {
                                Toasty.error(getApplicationContext(), "Error Msg : Many User !!", Toasty.LENGTH_LONG).show();

                            }
                        } else {
                            Toasty.error(getApplicationContext(), "Error Msg : There is No User Exist With Such Email.", Toasty.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        Toasty.error(getApplicationContext(), "Error Msg : " + e.getMessage(), Toasty.LENGTH_LONG).show();

                    }
                } else {
                    Toasty.error(getApplicationContext(), "Error Code : " + response.code(), Toasty.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recived_Sign_up>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Error Msg : " + t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }
}