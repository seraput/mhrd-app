package com.example.mhrd.Helper.Retrofit;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceDaily {
    @FormUrlEncoded
    @POST("insertActivity.php")
    Call<DailyReport> insertData(
            @Field("key") String key,
            @Field("jobs_id") String jobs_id,
            @Field("branch") String branch,
            @Field("project") String project,
            @Field("outlet") String outlet,
            @Field("product") String product,
            @Field("qty") String qty,
            @Field("image") String image,
            @Field("tanggal") String tanggal,
            @Field("user_id") String user_id);
}
