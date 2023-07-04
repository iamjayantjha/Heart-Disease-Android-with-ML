package com.heartdiseasedetection.application;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HeartAPI {
    @FormUrlEncoded
    @POST("/predict/")
    Call<Data> predict(@Field("age") String age, @Field("sex") String sex, @Field("cp") String cp, @Field("trestbps") String trestbps,
                       @Field("chol") String chol, @Field("fbs") String fbs, @Field("restecg") String restecg, @Field("thalach") String thalach,
                       @Field("exang") String exang, @Field("oldpeak") String oldpeak, @Field("slope") String slope, @Field("ca") String ca,
                       @Field("thal") String thal);

   /* @POST("/predict/")
    Call<ResponseBody> predict(@Body RequestBody data);*/
}
