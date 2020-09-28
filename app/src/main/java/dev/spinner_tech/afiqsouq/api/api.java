package dev.spinner_tech.afiqsouq.api;

import java.util.List;

import dev.spinner_tech.afiqsouq.Models.CategoryResp;
import dev.spinner_tech.afiqsouq.Models.LoginResp;
import dev.spinner_tech.afiqsouq.Models.ProductModel;
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
    Call<List<ProductModel>> getAllProducts(@Header("Authorization") String authHeader );

    @GET("user/generate_auth_cookie")
    Call<LoginResp> Login(@Query("username") String username , @Query("password") String pass) ;
    // get all the parent category   products/categories?parent=0
    @GET("products/categories?parent=0&per_page=100")
    Call<List<CategoryResp>> getAllParentCategory(@Header("Authorization") String authHeader );
    @GET("products/categories")
    Call<List<CategoryResp>> getAllSubCategory(@Header("Authorization")String authHeader ,
                                               @Query("parent") String parent ,
                                               @Query("per_page") String page );
   // products?category=403 product via category

   @GET("products")
   Call<List<ProductModel>> getAllProductViaCategory(@Header("Authorization") String authHeader ,
                                              @Query("category") String category);
}
