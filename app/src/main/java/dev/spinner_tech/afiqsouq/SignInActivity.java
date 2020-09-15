package dev.spinner_tech.afiqsouq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    private EditText nameET,passwordET;
    private Button signInBtn,facebookSignIn,twitterSignIn;
    private TextView forgetPassword,noAccountTV;
    private CheckBox rememberMeCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //editText
        nameET=findViewById(R.id.editText_Name);
        passwordET=findViewById(R.id.editText_password);

        //button
        signInBtn=findViewById(R.id.button_signin);
        facebookSignIn=findViewById(R.id.facebook_singin_button);
        twitterSignIn=findViewById(R.id.twitter_singin_button);
        //checkbox
        rememberMeCB=findViewById(R.id.remember_me_cb);
        //textview
        forgetPassword=findViewById(R.id.forget_password_tv);
        noAccountTV=findViewById(R.id.no_account_tv);
    }
}