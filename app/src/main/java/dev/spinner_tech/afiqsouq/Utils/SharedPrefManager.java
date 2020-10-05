package dev.spinner_tech.afiqsouq.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import dev.spinner_tech.afiqsouq.Models.PrefUserModel;


public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME_LOGIN = "newsrmeLogInprefs";
    private static final String SHARED_PREF_NAME = "newsrmesharedprefs";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String IS_USER_LOGGED_IN = "useremail";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_COUNTRY = "userrole";
    private static final String KEY_USER_ADDRESS = "adress";
    private static final String KEY_USER_PHONE = "usertype";
    private static final String KEY_USER_LANG_ID = "lang_id";
    private static final String KEY_USER_LANG_NAME = "lang_name";
    private  static  final String SHARED_PREF_NAME_SETTING = "settings";

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }

        return mInstance;
    }

    public boolean userLogin(String id, String name, String email, String adress, String country, String ph, String state) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_ID, id);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_ADDRESS, adress);
        editor.putString(KEY_USER_COUNTRY, country);
        editor.putString(KEY_USER_PHONE, ph);
        editor.putString("state", state);
        editor.apply();

        return true;
    }

    public String getUserAdress() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(KEY_USER_ADDRESS, null);

        return token;
    }

    public PrefUserModel getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//String name, String mail, String deliveryAddress, String country, String state, String id, String ph
        return new PrefUserModel(

                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_ADDRESS, null),
                sharedPreferences.getString(KEY_USER_COUNTRY, null),
                sharedPreferences.getString("state", null),
                sharedPreferences.getString(KEY_USER_ID, null),
                sharedPreferences.getString(KEY_USER_PHONE, null)
        );
    }

    /*public boolean isLogged() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_NAME, null) != null) {
            return true;
        }

        return false;
    }*/

    public void logout() {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_EMAIL, "null");
        editor.putString(IS_USER_LOGGED_IN, "no");



        editor.clear() ;
        editor.apply();

        SharedPreferences sharedPreferences1 = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();

        editor1.clear() ;
        editor1.apply();


    }

    public boolean isUserLoggedIn() {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String value = sharedPreferences.getString(IS_USER_LOGGED_IN, "no");
        return value.equals("yes");


    }

    public void saveUser(String email) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(IS_USER_LOGGED_IN, "yes");


        editor.apply();


    }

    public void saveLangPref(String id , String name ){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_LANG_ID, id);
        editor.putString(KEY_USER_LANG_NAME, name);
        editor.apply();
    }

    public   String[] getLangPref(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_SETTING, Context.MODE_PRIVATE);

        String id = sharedPreferences.getString(KEY_USER_LANG_ID, "1");
        String name = sharedPreferences.getString(KEY_USER_LANG_NAME, "en");

        /*
         array 0 is id
         array 1 is name
         */
        return new String[]{id , name };
    }

}
