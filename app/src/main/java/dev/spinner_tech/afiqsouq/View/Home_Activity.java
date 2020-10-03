package dev.spinner_tech.afiqsouq.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.viewPager2_adapter;
import dev.spinner_tech.afiqsouq.Helper.CurvedBottomNavigationView;
import dev.spinner_tech.afiqsouq.Models.TaxREsp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.SharedPrefManager;
import dev.spinner_tech.afiqsouq.View.Activities.CartListPage;
import es.dmoral.toasty.Toasty;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import meow.bottomnavigation.MeowBottomNavigation;

public class Home_Activity extends AppCompatActivity {

    CurvedBottomNavigationView navigation;
    VectorMasterView fab1, fab2, fab3;
    RelativeLayout lin_id;
    PathModel outline;
    ViewPager2 viewPager;
    ImageView image;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        navigation = findViewById(R.id.bottom_navigation_);
        viewPager = findViewById(R.id.view_pager);
        image = findViewById(R.id.imageView_discover_cart);
        title = findViewById(R.id.title);

        // getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, new dashboardFragment()).commit();
        viewPager.setAdapter(new viewPager2_adapter(Home_Activity.this));
        viewPager.setUserInputEnabled(false);
        navigation = (CurvedBottomNavigationView) findViewById(R.id.bottom_navigation_);
        navigation.inflateMenu(R.menu.menu);

        navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigation.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        navigation.getMenu().findItem(R.id.action_fav).setChecked(true);
                        break;

                }

                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPrefManager.getInstance(getApplicationContext())
                        .isUserLoggedIn()) {
                    Intent  p  = new Intent(getApplicationContext(), CartListPage.class);
                    startActivity(p);
                }


            }
        });


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =

            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    //   Fragment selectedFragmnet = null ;

                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            viewPager.setCurrentItem(0, false);
                            break;

                        case R.id.action_fav:
                            viewPager.setCurrentItem(1, false);
                            break;


                    }
                    //      getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, selectedFragmnet).commit();

                    return true;

                }
            };


    public void getViewPager() {
        if (null == viewPager) {
            viewPager = (ViewPager2) findViewById(R.id.view_pager) ;
        }

        viewPager.setCurrentItem(1);

    }
}