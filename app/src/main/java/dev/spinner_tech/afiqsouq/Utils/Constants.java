package dev.spinner_tech.afiqsouq.Utils;

import android.util.Log;

public  class Constants {
    public static final String ROOT_URL = "https://afiqsouq.com/wp-json/wc/v3/";
    public  static  final  String LOGIN_URL = "https://afiqsouq.com/api/" ;
    public static final String IMAGE_URL = "http://www.newsrme.com/";
    public static final String TAG = "NewsRme";
    public static final String CREATED_AT_FORMAT = "yyyy-MM-dd";
    public  static final String USER = "ck_62eed78870531071b419c0dca0b1dd9acf277227";
    public static final String KEY = "cs_a5b646ab7513867890dd63f2c504af98f00cee53";
    public static final String BASE = USER + ":"+KEY ;
    public static final String BDT_SIGN = "৳" ;
    public static  final  String COD = "cod" ;
    public static  final  String cashOnDelivery = "Cash on delivery" ;



    public  static  void PrintMsg(String msg){
        Log.d("TAG" , msg) ;
    }

//     call.enqueue(new Callback<List<ProductModel>>() {
//        @Override
//        public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
//
//            if(response.code()==200){
//                List<ProductModel> resp = response.body() ;
//
//                Log.d("TAG" , resp.size()+ " ") ;
//            }
//            else
//                Toast.makeText(getApplicationContext(), "Something Went Wrong " + response.code() , Toast.LENGTH_LONG).show();
//
//        }
//
//        @Override
//        public void onFailure(Call<List<ProductModel>> call, Throwable t) {
//
//            Toast.makeText(getApplicationContext(), "Something Went Wrong " + t.getMessage() , Toast.LENGTH_LONG).show();
//        }
//    });
}
