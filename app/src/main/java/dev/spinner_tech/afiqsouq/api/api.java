package dev.spinner_tech.afiqsouq.api;

import java.util.List;

import dev.spinner_tech.afiqsouq.Models.CategoryResp;
import dev.spinner_tech.afiqsouq.Models.LoginResp;
import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.Models.Recived_Sign_up;
import dev.spinner_tech.afiqsouq.Models.SignUpResp;
import dev.spinner_tech.afiqsouq.Models.VariationResp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface api {


    @POST("customers")
    Call<Recived_Sign_up> postUserRegister(@Header("Authorization") String authHeader, @Body SignUpResp body);

    @GET("products")
    Call<List<ProductModel>> getAllProducts(@Header("Authorization") String authHeader,
                                            @Query("per_page") String per_page,
                                            @Query("page") int page);

    @GET("user/generate_auth_cookie")
    Call<LoginResp> Login(@Query("username") String username, @Query("password") String pass);

    // get all the parent category   products/categories?parent=0
    @GET("products/categories?parent=0&per_page=100")
    Call<List<CategoryResp>> getAllParentCategory(@Header("Authorization") String authHeader);

    @GET("products/categories")
    Call<List<CategoryResp>> getAllSubCategory(@Header("Authorization") String authHeader,
                                               @Query("parent") String parent,
                                               @Query("per_page") String per_page);
    // products?category=403 product via category

    @GET("products")
    Call<List<ProductModel>> getAllProductViaCategory(@Header("Authorization") String authHeader,
                                                      @Query("category") String category);

    @GET("products")
    Call<List<ProductModel>> getAllProductViaCategoryID(@Header("Authorization") String authHeader,
                                                        @Query("category") String category,
                                                        @Query("per_page") String per_page,
                                                        @Query("page") int page
    );

    @GET("products")
    Call<List<ProductModel>> SearchProduct(@Header("Authorization") String authHeader,
                                                        @Query("search") String search
    );

    @GET("products/{p_id}/variations")
    Call<List<VariationResp>> ProductVariationList(@Header("Authorization") String authHeader,
                                                   @Path("p_id") String p_id
    );
}
