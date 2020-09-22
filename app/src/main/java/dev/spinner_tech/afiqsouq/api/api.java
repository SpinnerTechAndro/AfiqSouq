package dev.spinner_tech.afiqsouq.api;

import java.util.List;

import dev.spinner_tech.afiqsouq.Models.LoginResp;
import dev.spinner_tech.afiqsouq.Models.ProductResp;
import dev.spinner_tech.afiqsouq.Models.Recived_Sign_up;
import dev.spinner_tech.afiqsouq.Models.SignUpResp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public  interface api {


    @POST("customers")
    Call<Recived_Sign_up> postUserRegister(@Header("Authorization")String authHeader , @Body SignUpResp body);

    @GET("products")
    Call<List<ProductResp>> getAllProducts(@Header("Authorization") String authHeader );

    @GET("user/generate_auth_cookie")
    Call<LoginResp> Login(@Query("username") String username , @Query("password") String pass) ;

}
