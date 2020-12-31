package dev.mobile.afiqsouq.View.Activities;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.afiqsouq.BuildConfig;
import dev.mobile.afiqsouq.Models.TaxREsp;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.View.Home_Activity;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    private static final int RC_APP_UPDATE = 11;
    SharedPreferences shref;
    SharedPreferences.Editor editor;
    boolean isFirst = false;
    private AppUpdateManager mAppUpdateManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView versioNumber = findViewById(R.id.versionNumber);
        versioNumber.setText("V " + BuildConfig.VERSION_NAME);

        // request for tax list
        //   loadTax();
    }

    private void loadTax() {

        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        Call<List<TaxREsp>> listCall = RetrofitClient.getInstance()
                .getApi()
                .getAllTax(authHeader);

        listCall.enqueue(new Callback<List<TaxREsp>>() {
            @Override
            public void onResponse(Call<List<TaxREsp>> call, Response<List<TaxREsp>> response) {
                if (response.code() == 200) {
                    // noiw save the tax list
                    String key = "TAX_KEY";
                    List<TaxREsp> ModelArrayList = new ArrayList<>();

                    ModelArrayList = response.body();
                    shref = getSharedPreferences("TAX", MODE_PRIVATE);

                    Gson gson = new Gson();
                    String json = gson.toJson(ModelArrayList);

                    editor = shref.edit();
                    editor.putString(key, json);
                    editor.apply();
                    //  Toasty.normal(getApplicationContext() , ModelArrayList.size()+" " , Toasty.LENGTH_LONG).show();
                    goToHome();

                } else {
                    Toasty.error(getApplicationContext(), "Error : ReConnecting  " + response.code(), Toasty.LENGTH_SHORT).show();
                    ;

                    loadTax();
                }
            }

            @Override
            public void onFailure(Call<List<TaxREsp>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Error : ReConnecting  " + t.getMessage(), Toasty.LENGTH_SHORT).show();
                ;

                loadTax();
            }
        });
    }

    private void goToHome() {

        if (SharedPrefManager.getInstance(getApplicationContext()).isUserLoggedIn()) {
            Intent p = new Intent(getApplicationContext(), Home_Activity.class);
            startActivity(p);
            finish();
        } else {

            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            isFirst = prefs.getBoolean("firstStart", true);
            if (isFirst) {
                Intent o = new Intent(getApplicationContext(), Intro.class);
                o.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(o);
                finish();
            } else {

                Intent p = new Intent(getApplicationContext(), Sign_in.class);
                p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(p);
                finish();


            }


        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        //  mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE /*AppUpdateType.IMMEDIATE*/)) {
                Log.e("TAG", "Update Available ");

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, SplashScreen.this, RC_APP_UPDATE);

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else {
                loadTax();
                Log.e("TAG", "Update Not Available");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAppUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    mAppUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            this,
                                            RC_APP_UPDATE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }

//    InstallStateUpdatedListener installStateUpdatedListener = new
//            InstallStateUpdatedListener() {
//                @Override
//                public void onStateUpdate(InstallState state) {
//                     if (state.installStatus() == InstallStatus.INSTALLED){
//                        Log.d("TAG", "onStateUpdate: hey installed");
//                        if (mAppUpdateManager != null){
//                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(i);
//                            finish();
//                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
//                        }
//
//                    }
//                }
//            };


}