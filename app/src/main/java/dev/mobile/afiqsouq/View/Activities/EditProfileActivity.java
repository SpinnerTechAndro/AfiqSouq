package dev.mobile.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import dev.mobile.afiqsouq.Models.Recived_Sign_up;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    AutoCompleteTextView districtList, countryList;
    TextInputEditText fnamein, emailIn, passIn, deliveryAddress, phonein, snamein;
    CheckBox terms;
    MaterialButton signUpBtn;
    String fname, sname, email, pass, deliverAdress, country, district, ph;
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        // set up view
        districtList = findViewById(R.id.dlist);
        countryList = findViewById(R.id.countryIn);
        fnamein = findViewById(R.id.fname);
        snamein = findViewById(R.id.sname);
        emailIn = findViewById(R.id.email);
        passIn = findViewById(R.id.pass);
        deliveryAddress = findViewById(R.id.deliveryAddress);
        terms = findViewById(R.id.checkbox);
        signUpBtn = findViewById(R.id.button_signin);
        phonein = findViewById(R.id.phone);
        spinKitView = findViewById(R.id.spin_kit);
        spinKitView.setVisibility(View.VISIBLE);
        requestForDetails(Integer.parseInt(SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()));
    }

    private void requestForDetails(int id) {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        Call<Recived_Sign_up> signUpRespCall = RetrofitClient
                .getInstance()
                .getApi()
                .getSingleData(authHeader, id);

        signUpRespCall.enqueue(new Callback<Recived_Sign_up>() {
            @Override
            public void onResponse(Call<Recived_Sign_up> call, Response<Recived_Sign_up> response) {
                if (response.code() == 200) {
                    spinKitView.setVisibility(View.GONE);
                    Recived_Sign_up userModel = response.body();
                    try {
                        if (userModel != null) {
                            // save user
                            //String id, String name, String email, String adress, String country, String ph, String state)
//                            SharedPrefManager.getInstance(getApplicationContext())
//                                    .userLogin(
//                                            userModel.getId()+"",
//                                            userModel.getUsername(),
//                                            userModel.getEmail(),
//                                            userModel.getShipping().getAddress1() ,
//                                            userModel.getShipping().getCountry() ,
//                                            userModel.getBilling().getPhone(),
//                                            userModel.getShipping().getState()
//
//                                    );
//                            SharedPrefManager.getInstance(getApplicationContext()).saveUser(userModel.getUsername());

                            fnamein.setText(userModel.getFirstName());
                            snamein.setText(userModel.getBilling().getCountry());
                            //pass = passIn.getText().toString();
                            countryList.setText(userModel.getBilling().getCountry());
                            districtList.setText(userModel.getBilling().getState());
                            //pass = passIn.getText().toString();
                            emailIn.setText(userModel.getEmail());
                            deliveryAddress.setText(userModel.getShipping().getAddress1() );
                            phonein.setText(userModel.getBilling().getPhone());


                        } else {
                            spinKitView.setVisibility(View.GONE);
                            Toasty.error(getApplicationContext(), "Error : User Data Could Not Be Found ", Toasty.LENGTH_SHORT).show();

                        }
                    } catch (Exception r) {
                        spinKitView.setVisibility(View.GONE);
                        Toasty.error(getApplicationContext(), "Error : " + r.getMessage(), Toasty.LENGTH_SHORT).show();

                    }
                } else {
                    spinKitView.setVisibility(View.GONE);
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), Toasty.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Recived_Sign_up> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });


    }


}