package dev.spinner_tech.afiqsouq.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.viewPager2_adapter;
import dev.spinner_tech.afiqsouq.Helper.CurvedBottomNavigationView;
import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import dev.spinner_tech.afiqsouq.Models.PrefUserModel;
import dev.spinner_tech.afiqsouq.Models.TaxREsp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.SharedPrefManager;
import dev.spinner_tech.afiqsouq.View.Activities.CartListPage;
import dev.spinner_tech.afiqsouq.View.Activities.Category_List;
import dev.spinner_tech.afiqsouq.View.Activities.Top_Recent_List;
import dev.spinner_tech.afiqsouq.database.CartDatabase;
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
    ImageView image, sideBar;
    TextView appBarTitle, cartCount;
    FloatingActionButton floatingActionButton;
    CartDatabase cartDatabase;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        navigation = findViewById(R.id.bottom_navigation_);
        viewPager = findViewById(R.id.view_pager);
        image = findViewById(R.id.imageView_discover_cart);
        appBarTitle = findViewById(R.id.title);
        floatingActionButton = findViewById(R.id.fav);
        sideBar = findViewById(R.id.imageview_discover_toolbar_left);
        cartCount = findViewById(R.id.textview_discover_cartNumber);

        cartDatabase = Room.databaseBuilder(getApplicationContext(),
                CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        // getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, new dashboardFragment()).commit();
        viewPager.setAdapter(new viewPager2_adapter(Home_Activity.this));
        viewPager.setUserInputEnabled(false);
        navigation = (CurvedBottomNavigationView) findViewById(R.id.bottom_navigation_);
        navigation.inflateMenu(R.menu.menu);

        // Log.d("TAG", "onCreate: " + SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
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

                    case 2:
                        navigation.getMenu().findItem(R.id.action_search).setChecked(true);
                        break;
                    case 3:
                        navigation.getMenu().findItem(R.id.action_profile).setChecked(true);
                        break;

                }

                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPrefManager.getInstance(getApplicationContext())
                        .isUserLoggedIn()) {
                    Intent p = new Intent(getApplicationContext(), CartListPage.class);
                    startActivity(p);
                }


            }
        });

        sideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogue();
            }
        });
    }

    private void openDialogue() {
        LinearLayout categoryTv, recentTv, topDealsTv;
        TextView name, email;
        CircularImageView cancelBtn;
        Dialog dialog = new Dialog(Home_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.menu_dialoge);
        // views of  the dialogue
        categoryTv = dialog.findViewById(R.id.categories_tv);
        recentTv = dialog.findViewById(R.id.new_arrivals_tv);
        name = dialog.findViewById(R.id.person_name_tv);
        email = dialog.findViewById(R.id.person_email_tv);
        cancelBtn = dialog.findViewById(R.id.close_btn_id);
        topDealsTv = dialog.findViewById(R.id.top_deals_tv);

        //
        PrefUserModel model = SharedPrefManager.getInstance(getApplicationContext())
                .getUser();

        name.setText(model.getName());
        email.setText(model.getMail());
        topDealsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), Top_Recent_List.class);
                p.putExtra("TYPE", "TOP");
                startActivity(p);
            }
        });


        categoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), Category_List.class);
                startActivity(p);
            }
        });

        recentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), Top_Recent_List.class);
                p.putExtra("TYPE", "RECENT");
                startActivity(p);
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =

            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    //   Fragment selectedFragmnet = null ;

                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            setHeaderTitle("Discover");
                            viewPager.setCurrentItem(0, false);
                            break;

                        case R.id.action_fav:
                            setHeaderTitle("Category");
                            viewPager.setCurrentItem(1, false);
                            break;

                        case R.id.action_search:
                            setHeaderTitle("Search");
                            viewPager.setCurrentItem(2, false);
                            break;
                        case R.id.action_profile:
                            setHeaderTitle("Profile");
                            viewPager.setCurrentItem(3, false);
                            break;


                    }
                    //      getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, selectedFragmnet).commit();

                    return true;

                }
            };


    public void getViewPager() {
        if (null == viewPager) {
            viewPager = (ViewPager2) findViewById(R.id.view_pager);
        }

        viewPager.setCurrentItem(2);

    }

    public void setHeaderTitle(String title) {
        appBarTitle.setText(title);
    }

    public void countCartItemNumber() {

        try {
            List<CartDbModel> models = cartDatabase.dao().fetchAllTodos();
            cartCount.setText(models.size() + "");
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        countCartItemNumber();
        super.onResume();

    }
}