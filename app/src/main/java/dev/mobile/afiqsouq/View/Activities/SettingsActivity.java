package dev.mobile.afiqsouq.View.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.onesignal.OneSignal;

import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import es.dmoral.toasty.Toasty;

public class SettingsActivity extends AppCompatActivity {
    SwitchMaterial toggleNotificaiton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toggleNotificaiton = findViewById(R.id.toogleNottificaiton);

        if(SharedPrefManager.getInstance(getApplicationContext()).getNottificationStatus()){
            toggleNotificaiton.setChecked(true);
        }
        else {
            toggleNotificaiton.setChecked(false);
        }

        toggleNotificaiton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPrefManager.getInstance(getApplicationContext()).setNottificationStatus(isChecked);
                if(isChecked){
                    OneSignal.setSubscription(true);
                    OneSignal.enableVibrate(true);
                    OneSignal.enableSound(true);
                    Toasty.success(getApplicationContext(), "Notifications Are Enabled !!", 1).show();
                }
                else {
                    OneSignal.setSubscription(false);
                    Toasty.success(getApplicationContext(), "Notifications Are Disabled  !!", 1).show();
                }


            }
        });

        findViewById(R.id.privacyPolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://afiqsouq.com/privacy-policy/"));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.resetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(p);
            }
        });
    }
}