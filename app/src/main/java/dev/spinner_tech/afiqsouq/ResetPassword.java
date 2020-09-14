package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {

    //toolbar
    ImageView back;

    //content
    EditText email;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //toolbar
        back=findViewById(R.id.imageview_resetpass_back);

        //enter mail and press reset button to reset password
        email=findViewById(R.id.editText_resetpass_EmailAddress);
        reset=findViewById(R.id.button_resetPass_reset);
    }
}