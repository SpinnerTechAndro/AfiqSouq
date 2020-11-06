package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroPageTransformerType;

import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.View.Fragment.FourthFragment;
import dev.spinner_tech.afiqsouq.View.Fragment.FristFragment;
import dev.spinner_tech.afiqsouq.View.Fragment.SecondFragment;
import dev.spinner_tech.afiqsouq.View.Fragment.ThirdFragment;

public class Intro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note here that we DO NOT use setContentView();
        // Add your slide fragments here.
        setBarColor(Color.parseColor("#008577"));
        addSlide(new FristFragment());
        addSlide(new SecondFragment());
        addSlide(new ThirdFragment());
        addSlide(new FourthFragment());
        setTransformer(AppIntroPageTransformerType.Depth.INSTANCE);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.

        Intent i = new Intent(getApplicationContext()  , Sign_in.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.

        Intent i = new Intent(getApplicationContext()  , Sign_in.class);
        startActivity(i);
        finish();

    }



    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.


    }
}

