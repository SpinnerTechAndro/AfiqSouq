package dev.mobile.afiqsouq.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import org.json.JSONObject;

import java.util.List;

import dev.mobile.afiqsouq.Adapter.viewPager2_adapter;
import dev.mobile.afiqsouq.Helper.CurvedBottomNavigationView;
import dev.mobile.afiqsouq.LiveChat;
import dev.mobile.afiqsouq.Models.CartDbModel;
import dev.mobile.afiqsouq.Models.PrefUserModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.View.Activities.CartListPage;
import dev.mobile.afiqsouq.View.Activities.Category_List;
import dev.mobile.afiqsouq.View.Activities.Top_Recent_List;
import dev.mobile.afiqsouq.database.CartDatabase;

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
        writeOnSharedPref();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
               // .setNotificationOpenedHandler(new notificationOpenHandler())
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        if(SharedPrefManager.getInstance(getApplicationContext()).getNottificationStatus()){
            OneSignal.setSubscription(true);
            OneSignal.enableVibrate(true);
            OneSignal.enableSound(true);
        }
        else {
            OneSignal.setSubscription(false);
        }

        startService(new Intent(this, FireBase_notification.class));
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
        //throw new RuntimeException("Test Crash"); // Force a crash
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(Home_Activity.this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Home_Activity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void openDialogue() {
        LinearLayout categoryTv, recentTv, topDealsTv, chatTv;
        TextView name, email;
        CircularImageView cancelBtn;
        Dialog dialog = new Dialog(Home_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.menu_dialoge);
        // views of  the dialogue
        categoryTv = dialog.findViewById(R.id.categories_tv);
        recentTv = dialog.findViewById(R.id.new_arrivals_tv);
        chatTv = dialog.findViewById(R.id.help_tv) ;
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

        chatTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), LiveChat.class);
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
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   cartCount.setText(models.size() + "");
               }
           });
        } catch (Exception e) {

        }


    }

    public  void   writeOnSharedPref()
    {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

    }

    @Override
    protected void onResume() {
        countCartItemNumber();
        super.onResume();

    }
    public class notificationOpenHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            //   String title = result.notification.payload.title;
            String desc = result.notification.payload.body;
            //  String f = result.notification.payload.groupKey

            // Intent intent = new Intent(getApplicationContext(), NottificationPage.class);
            //  intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            //  startActivity(intent);
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String post_type  , id ;
            Log.d("TAG", "opended");

            Log.d("TAG", "result.notification.payload.toJSONObject().toString(): " + result.notification.payload.toJSONObject().toString());


                    //Log.d("TAG", "key set with value: " + post_type);
                    Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
                    startActivity(intent);




            }




        }

    }

