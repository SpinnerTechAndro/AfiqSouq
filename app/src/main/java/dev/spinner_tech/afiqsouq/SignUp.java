package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    EditText name,email,password;
    CheckBox service_and_privacy;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editText_signup_Name);
        email = findViewById(R.id.editText_signup_Email);
        password = findViewById(R.id.editText_signup_Password);
        service_and_privacy = findViewById(R.id.checkbox_signup_servicePrivacy); //agree with term of service and privacy condition
        signUp = findViewById(R.id.button_signup);
    }
}