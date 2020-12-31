package dev.mobile.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import dev.mobile.afiqsouq.Models.SignUpResp;
import dev.mobile.afiqsouq.R;
import es.dmoral.toasty.Toasty;

public class SignUP extends AppCompatActivity {

    AutoCompleteTextView districtList, countryList;
    TextInputEditText fnamein, emailIn, passIn, deliveryAddress, phonein  , snamein , userName;
    CheckBox terms;
    MaterialButton signUpBtn;
    String fname ,sname, email, pass, deliverAdress, country, district, ph , username;
    SpinKitView spinKitView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_u_p);

        // set up view
        districtList = findViewById(R.id.dlist);
        countryList = findViewById(R.id.countryIn);
        fnamein = findViewById(R.id.fname);
        snamein =findViewById(R.id.sname) ;
        emailIn = findViewById(R.id.email);
        passIn = findViewById(R.id.pass);
        deliveryAddress = findViewById(R.id.deliveryAddress);
        terms = findViewById(R.id.checkbox);
        signUpBtn = findViewById(R.id.button_signin);
        phonein = findViewById(R.id.phone);
        spinKitView = findViewById(R.id.spin_kit);
        spinKitView.setVisibility(View.GONE);
        userName = findViewById(R.id.userName) ;



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.bd_districts));

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.country));

        districtList.setAdapter(adapter);
        countryList.setAdapter(countryAdapter);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (terms.isChecked()) {
                    fname = fnamein.getText().toString();
                    sname = snamein.getText().toString();
                    pass = passIn.getText().toString();
                    country = countryList.getText().toString();
                    district = districtList.getText().toString();
                    pass = passIn.getText().toString();
                    email = emailIn.getText().toString();
                    deliverAdress = deliveryAddress.getText().toString();
                    ph = phonein.getText().toString();
                    username= userName.getText().toString() ;

                    // check if thery are empty are not

                    if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(sname) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(country) || TextUtils.isEmpty(district) ||
                            TextUtils.isEmpty(email) || TextUtils.isEmpty(deliverAdress) || TextUtils.isEmpty(ph)|| TextUtils.isEmpty(username)) {
                        Toasty.error(getApplicationContext(), "Fill Data Properly!!", 1).show();
                    } else {

                        RegisterUser();
                    }

                } else {
                    Toasty.error(getApplicationContext(), "Please Agree To Our Terms & Conditions", 1).show();
                }

            }
        });

    }

    public void RegisterUser() {
        spinKitView.setVisibility(View.GONE);
        if (country.toLowerCase().equals("bangladesh")) {
            country = "BD";
        } else {
            country = "PH";
        }


        SignUpResp.Billing billingModel = new SignUpResp.Billing(fname, sname, "",
                deliverAdress, "", "" + district, "" + district, "", country, email
                , ph);
//String firstName, String lastName, String company, String address1, String address2, String city, String state, String postcode, String country
        SignUpResp.Shipping shippingModel = new SignUpResp.Shipping("" + fname, ""+sname, " ",
                deliverAdress, "", district, "", "", country);

        SignUpResp resp = new SignUpResp(email, pass, "" + fname,"" +sname, ""+username, billingModel, shippingModel);


//        Call<Recived_Sign_up> call = RetrofitClient.getInstance().getApi().
//                postUserRegister(authHeader, resp);
//
//        call.enqueue(new Callback<Recived_Sign_up>() {
//            @Override
//            public void onResponse(Call<Recived_Sign_up> call, Response<Recived_Sign_up> response) {
//                // Constants.PrintMsg("Error : CODE  " + response.code());
//                if (response.code() == 201) {
//
//
//                    Recived_Sign_up model = response.body();
//                    SignUpResp.Shipping shippingMOdel = model.getShipping();
//
//
//                    //  String id, String name, String email, String adress, String country, String ph, String state
//
//                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(
//                            model.getId() + "",
//                            model.getUsername(),
//                            model.getEmail(),
//                            shippingMOdel.getAddress1(),
//                            shippingMOdel.getCountry(),
//                            model.getBilling().getPhone(),
//                            shippingMOdel.getState()
//
//                    );
//
//                    SharedPrefManager.getInstance(getApplicationContext())
//                            .saveUser(model.getEmail());
//
//                    spinKitView.setVisibility(View.GONE);
//
//                    // saved  the data
//                    Intent p = new Intent(getApplicationContext(), Home_Activity.class);
//                    startActivity(p);
//
//
//                } else {
//                    spinKitView.setVisibility(View.GONE);
//                    Constants.PrintMsg("Error : CODE  " + response.code());
//                    if (response.code() == 400) {
//                        Toasty.error(getApplicationContext(), "An account is already registered with your email address or User name ",
//                                1
//                        ).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Recived_Sign_up> call, Throwable t) {
//
//                Constants.PrintMsg("Error : " + t.getMessage());
//            }
//        });

        Intent p = new Intent(getApplicationContext(), otpAcitivity.class) ;
        p.putExtra("MODELS" , resp) ;
        startActivity(p);

    }
}