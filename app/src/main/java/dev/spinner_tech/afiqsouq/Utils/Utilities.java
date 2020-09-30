package dev.spinner_tech.afiqsouq.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import es.dmoral.toasty.Toasty;

public class Utilities {

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("USER", Context.MODE_PRIVATE);
    }

    public void showMsg(Context context, String msg) {

        Toasty.warning(context, msg, Toast.LENGTH_LONG).show();

    }

    public double calculateTotal(List<CartDbModel> todoList) {
        double totalAmount = 0.0;

        for (CartDbModel item : todoList) {
            totalAmount = totalAmount + (item.unit_price * item.qty);
        }


        return totalAmount;

    }


    public String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        System.out.println("Current time => " + formattedDate);
        return formattedDate;

    }

    public String getDay(String datee) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String day = "0" ;
        try {
            date = format.parse(datee);
            day = (String) DateFormat.format("dd", date); // 20
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
    public String getMonth(String datee) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String month = "0" ;
        int monNumber  = 0 ;
        try {
            date = format.parse(datee);
            month = (String) DateFormat.format("MM", date); // 20
            monNumber = Integer.parseInt(month) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getMonthName(monNumber-1, Locale.US, true);
    }

    private String getMonthName(final int index, final Locale locale, final boolean shortName)
    {
        String format = "%tB";

        if (shortName)
            format = "%tb";

        Calendar calendar = Calendar.getInstance(locale);
        calendar.set(Calendar.MONTH, index);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return String.format(locale, format, calendar);
    }

    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
