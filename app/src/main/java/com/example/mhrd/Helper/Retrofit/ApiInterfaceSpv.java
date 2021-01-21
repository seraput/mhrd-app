package com.example.mhrd.Helper.Retrofit;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceSpv {
    @FormUrlEncoded
    @POST("insertAktivitas.php")
    Call<Absent> insertData(
            @Field("key") String key,
            @Field("user_id") String userId,
            @Field("nama") String userNama,
            @Field("tanggal") String tanggal,
            @Field("jam") String jam,
            @Field("image") String image,
            @Field("keterangan") String keterangan);
}
