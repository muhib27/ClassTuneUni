package com.classtune.classtuneuni.retrofit;


import com.classtune.classtuneuni.model.LoginApiModel;
import com.classtune.classtuneuni.utils.URLHelper;
import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


//import io.reactivex.Observable;


/**
 * Created by RR on 27-Dec-17.
 */

public interface ApiInterface {


    @FormUrlEncoded
    @POST(URLHelper.ADD_FCM)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<JsonElement>> addFcm(@Field("fcm_id") String fcm_id);

    @FormUrlEncoded
    @POST(URLHelper.URL_LOGIN)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<LoginApiModel>> userLogin(@Field("email") String userId, @Field("password") String password, @Field("fcm_id") String gcm_id);

    @FormUrlEncoded
    @POST(URLHelper.GET_LOGOUT)
    Observable<Response<JsonElement>> getLogout(@Field("api_key") String api_key, @Field("fcm_id") String fcm_id);

}


