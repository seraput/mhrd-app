package com.example.mhrd.Helper.Retrofit;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("insertAbsent.php")
    Call<Absent> insertData(
            @Field("key") String key,
            @Field("u_id") String userId,
            @Field("u_nama") String userNama,
            @Field("p_id") String projectId,
            @Field("p_nama") String projectNama,
            @Field("tanggal") String tanggal,
            @Field("jam") String jam,
            @Field("image") String image,
            @Field("keterangan") String keterangan);
}
