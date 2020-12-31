package dev.mobile.afiqsouq.api;

import java.util.List;

import dev.mobile.afiqsouq.Models.CategoryResp;
import dev.mobile.afiqsouq.Models.CouponModel;
import dev.mobile.afiqsouq.Models.CouponResp;
import dev.mobile.afiqsouq.Models.CreateOrderResp;
import dev.mobile.afiqsouq.Models.CreateReviewRep;
import dev.mobile.afiqsouq.Models.LoginResp;
import dev.mobile.afiqsouq.Models.OrderResp;
import dev.mobile.afiqsouq.Models.PaymentDone;
import dev.mobile.afiqsouq.Models.PreviousWalletTransModel;
import dev.mobile.afiqsouq.Models.ProductModel;
import dev.mobile.afiqsouq.Models.Recived_Sign_up;
import dev.mobile.afiqsouq.Models.ResetPassword;
import dev.mobile.afiqsouq.Models.ReviewResp;
import dev.mobile.afiqsouq.Models.SignUpResp;
import dev.mobile.afiqsouq.Models.TaxREsp;
import dev.mobile.afiqsouq.Models.UpdateOrderModel;
import dev.mobile.afiqsouq.Models.VariationResp;
import dev.mobile.afiqsouq.Models.deliveryZoneResp;
import dev.mobile.afiqsouq.Models.oldOrderModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface api {


    @POST("customers")
    Call<Recived_Sign_up> postUserRegister(@Header("Authorization") String authHeader, @Body SignUpResp body);

    // get single data

    @GET("customers/{id}")
    Call<Recived_Sign_up> getSingleData(@Header("Authorization") String authHeader, @Path("id") int id );

    @GET("products")
    Call<List<ProductModel>> getAllProducts(@Header("Authorization") String authHeader,
                                            @Query("per_page") String per_page,
                                            @Query("page") int page);

    @GET("products")
    Call<List<ProductModel>> getAllProducts(@Header("Authorization") String authHeader,
                                            @Query("per_page") String per_page,
                                            @Query("page") int page,
                                            @Query("orderby") String orderby,
                                            @Query("order") String orderby1);

    // getting the balance
    @GET("current_balance/{id}")
    Call<String>getCurrentBal(@Header("Authorization") String authHeader,
                                    @Path("id") String  user_id) ;

    // transactionList
    @GET("wallet/{id}")
    Call<List<PreviousWalletTransModel>>getTransList(@Header("Authorization") String authHeader,
                                                     @Path("id") String  user_id) ;

    // popular product list
    @GET("products?per_page=10&orderby=date&order=desc")
    Call<List<ProductModel>> getAllRecentProducts(@Header("Authorization") String authHeader
            , @Query("per_page") int per_page);
    @GET("products?per_page=10&orderby=popularity&order=desc")
    Call<List<ProductModel>> getAllFeaturedProducts(@Header("Authorization") String authHeader , @Query("per_page") int page );

    @GET("user/generate_auth_cookie")
    Call<LoginResp> Login(@Query("email") String username, @Query("password") String pass);

    // get all the parent category   products/categories?parent=0
    @GET("products/categories?parent=0&per_page=100&exclude=15")
    Call<List<CategoryResp>> getAllParentCategory(@Header("Authorization") String authHeader);

    // tax list
    @GET("taxes")
    Call<List<TaxREsp>> getAllTax(@Header("Authorization") String authHeader);

    @GET("products/categories")
    Call<List<CategoryResp>> getAllSubCategory(@Header("Authorization") String authHeader,
                                               @Query("parent") String parent,
                                               @Query("per_page") String per_page,
                                               @Query("exclude") String exclude);


    // products?category=403 product via category

    @GET("products")
    Call<List<ProductModel>> getAllProductViaCategory(@Header("Authorization") String authHeader,
                                                      @Query("category") String category,
                                                      @Query("per_page") String per_page,
                                                      @Query("page") int page);

    @GET("products")
    Call<List<ProductModel>> getAllProductViaCategoryID(@Header("Authorization") String authHeader,
                                                        @Query("category") String category,
                                                        @Query("per_page") String per_page,
                                                        @Query("page") int page
    );
    @GET("products")
    Call<List<ProductModel>> getAllProductViaCategoryIDForLaunch(@Header("Authorization") String authHeader,
                                                        @Query("category") String category,
                                                        @Query("per_page") String per_page,
                                                        @Query("page") int page
    );

    @GET("products")
    Call<List<ProductModel>> SearchProduct(@Header("Authorization") String authHeader,
                                                        @Query("search") String search,
                                                        @Query("per_page") int per_page
    );

    //products/reviews?product=5151

    @GET("products/reviews")
    Call<List<ReviewResp>> getProductReview(@Header("Authorization") String authHeader,
                                         @Query("product") int product_id
    );

    @GET("shipping/zones/{id}/methods")
    Call<List<deliveryZoneResp>> getDeliveryCharge(@Header("Authorization") String authHeader ,
                                                   @Path("id") String id
    );


    @GET("products/{p_id}/variations")
    Call<List<VariationResp>> ProductVariationList(@Header("Authorization") String authHeader,
                                                   @Path("p_id") String p_id
    );
    @POST("orders")
    Call<OrderResp> CreateOrder(@Header("Authorization") String authHeader, @Body CreateOrderResp body);

    //orders?customer=13
    @GET("orders")
    Call<List<oldOrderModel>> oldOrderList(@Header("Authorization") String authHeader,
                                           @Query("customer") String c_id) ;

    @GET("coupons?per_page=100")
    Call<List<CouponModel>> getCoupons(@Header("Authorization") String authHeader) ;

    @POST("products/reviews")
    Call<ReviewResp> CreateReviews(@Header("Authorization") String authHeader,
                                   @Body CreateReviewRep body);

    // get Coupon
    @GET("coupons")
    Call<List<CouponResp>> getTheCoupon(@Header("Authorization") String authHeader,
                                        @Query("code") String c_id) ;

    @GET("api.php")
    Call<String>sendSms(@Query("username")String username,
                        @Query("password")String password,
                         @Query("phone")String ph,
                        @Query("msg")String msg ) ;

    // search for user
    @GET("customers")
    Call<List<Recived_Sign_up>> SearchUser(@Header("Authorization") String authHeader, @Query("email") String email );

    @PUT("customers/{id}")
    Call<Recived_Sign_up> resetPass(@Header("Authorization") String authHeader, @Body ResetPassword body , @Path("id") int id  );
// update order status


    @PUT("orders/{id}")
    Call<oldOrderModel> updateOrderStatus(@Header("Authorization") String authHeader, @Body UpdateOrderModel body , @Path("id") int id  );

    // deduct balance
    @POST("wallet/{id}")
    Call<PaymentDone> deductBalance(@Header("Authorization") String authHeader , @Path("id") String id ,
                                    @Query("type")String type ,
                                    @Query("details")String details ,
                                    @Query("amount")double amount ) ;


}
