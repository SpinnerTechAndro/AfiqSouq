package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import dev.spinner_tech.afiqsouq.Models.ProductResp;
import dev.spinner_tech.afiqsouq.Models.Recived_Sign_up;
import dev.spinner_tech.afiqsouq.Models.SignUpResp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_u_p);

        String authHeader ="Basic "+ Base64.encodeToString(Constants.BASE.getBytes() , Base64.NO_WRAP) ;

        SignUpResp.Billing billingModel  = new SignUpResp.Billing("Rahat", "Shovo" , "",
                "ADdress" , "" , "" , "" , "", "" , "emadfdil1@gmail.com"
        ,"01516185792") ;
//String firstName, String lastName, String company, String address1, String address2, String city, String state, String postcode, String country
        SignUpResp.Shipping shippingModel = new SignUpResp.Shipping("Rahat", "Shovo" , "test",
                "ADdress"  , "" , "ADdress" , "", "" , "BD") ;

        SignUpResp resp = new SignUpResp("raa1@gmail.com" , "1234567856" , "rahat" , "Shovo" , "Rahatshovo" ,billingModel , shippingModel ) ;
        Call<Recived_Sign_up> call = RetrofitClient.getInstance().getApi().
                postUserRegister(authHeader, resp) ;


        call.enqueue(new Callback<Recived_Sign_up>() {
            @Override
            public void onResponse(Call<Recived_Sign_up> call, Response<Recived_Sign_up> response) {
                Constants.PrintMsg("Error : CODE  "+ response.code() );
                if(response.code() == 201){

                    Recived_Sign_up model = response.body() ;

                    Log.d("TAG", "onResponse: " + model.getId());

                }
                else {
                    Constants.PrintMsg("Error : CODe  "+ response.code() );
                }

            }

            @Override
            public void onFailure(Call<Recived_Sign_up> call, Throwable t) {

            Constants.PrintMsg("Error : "+ t.getMessage() );
            }
        });



    }
}