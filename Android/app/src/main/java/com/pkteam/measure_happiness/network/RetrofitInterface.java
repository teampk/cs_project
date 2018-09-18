package com.pkteam.measure_happiness.network;

import com.pkteam.measure_happiness.model.Res;
import com.pkteam.measure_happiness.model.User;
import com.pkteam.measure_happiness.model.Value;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {

    @POST("users")
    Observable<Res> register(@Body User user);

    @POST("authenticate")
    Observable<Res> login();

    @POST("register")
    Observable<Res> registerValue(@Body Value surveyValue);

    @GET("getValue/{userId}")
    Observable<Value[]> getValue(@Path("userId") String userId);


}
