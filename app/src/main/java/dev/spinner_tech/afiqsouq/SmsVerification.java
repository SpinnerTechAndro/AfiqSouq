package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

public class SmsVerification extends AppCompatActivity {

    ImageView back;
    TextView codesent_number;
    PinView pinview;
    Button verify,cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verification);

        //toolbar
        back=findViewById(R.id.imageview_verification_back);

        //content
        codesent_number=findViewById(R.id.textview_verification_codesent); //Phone number where verification code is sent
        pinview= findViewById(R.id.pinView_verificationn_OTP); //enter verification code here
        verify=findViewById(R.id.button_verification_verify);
        cancel=findViewById(R.id.button_verification_cancel);
    }
}