package com.technest.needfood.network;

import com.technest.needfood.models.alat.ResponseAlat;
import com.technest.needfood.models.alat.ResponseAlatPesanan;
import com.technest.needfood.models.alat.ResponseAllAlat;
import com.technest.needfood.models.bahan.ResponseAllBahan;
import com.technest.needfood.models.bahan.ResponseBahan;
import com.technest.needfood.models.intro.LoginModel;
import com.technest.needfood.models.kategori.ResponKategori;
import com.technest.needfood.models.keuangan.ResponseKeuangan;
import com.technest.needfood.models.keuangan.ResponseKeuanganMingguan;
import com.technest.needfood.models.keuangan.ResponseKeuanganSingle;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.models.pesanan.ResponsePesananID;
import com.technest.needfood.models.user.ResponAdmin;
import com.technest.needfood.models.user.ResponDriver;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //KEUANGAN
    @GET("keuangan/getdata")
    Call<ResponseKeuangan> getKeuangan(@Header("Authorization") String authToken,
                                       @Query("jenis") String jenis,
                                       @Query("waktu") String waktu);

    @GET("keuangan/getdata/{id}")
    Call<ResponseKeuanganSingle> getKeuanganSingle(@Header("Authorization") String authToken,
                                                   @Path("id") String id);

    @GET("keuangan/datathisweek")
    Call<ResponseKeuanganMingguan> getKeuanganMingguan(@Header("Authorization") String authToken);


    // PESANAN
    @GET("datapesanan")
    Call<ResponsePesanan> getPesanan(@Header("Authorization") String authToken);

    @GET("datapesanan/{id}")
    Call<ResponsePesananID> getPesananId(@Header("Authorization") String authToken,
                                         @Path("id") String id);

    @GET("datapesanan")
    Call<ResponsePesanan> getPesananSatus(@Header("Authorization") String authToken,
                                          @Query("status") String status);

    @GET("datapesanan/pesanan/today")
    Call<ResponsePesanan> getPesananHariIni(@Header("Authorization") String authToken,
                                            @Query("status") String status,
                                            @Query("tanggal") String tanggal);

    @FormUrlEncoded
    @PUT("datapesanan/updatestatus/{id}")
    Call<ResponsePesanan> updateSatusPesanan(@Header("Authorization") String authToken,
                                             @Path("id") String id,
                                             @Field("status") String status);

    @FormUrlEncoded
    @PUT("datapesanan/updatedriver/{id}")
    Call<ResponsePesanan> updateDriverPesanan(@Header("Authorization") String authToken,
                                              @Path("id") String id,
                                              @Field("driver_id") String driver_id,
                                              @Field("status") String status);

    @GET("datapesanan/driver/{driver_id}")
    Call<ResponsePesanan> getPesananDriver(@Header("Authorization") String authToken,
                                           @Path("driver_id") String driver_id,
                                           @Query("status") String status);

    @GET("datapesanan/getalatpesanan/{pemesanan_id}")
    Call<ResponseAlatPesanan> getAlatPesanan(@Header("Authorization") String authToken,
                                             @Path("pemesanan_id") String pemesanan_id);

    @Multipart
    @POST("driver/fotopesanan/{pesanan_id}")
    Call<ResponsePesanan> setFotoPesanan(@Header("Authorization") String authToken,
                                         @Part MultipartBody.Part foto,
                                         @Path("pesanan_id") String pesanan_id);


    // KATEGORI
    @GET("inventori/getkategori/")
    Call<ResponKategori> getKategori(@Header("Authorization") String authToken,
                                     @Query("kategori") String kategori);


    // BAHAN
    @GET("inventori/getbahan/kategori/{id}")
    Call<ResponseAllBahan> getAllBahan(@Header("Authorization") String authToken,
                                       @Path("id") String id);

    @GET("inventori/getbahan/{id}")
    Call<ResponseBahan> getBahan(@Header("Authorization") String authToken,
                                 @Path("id") String id);


    // ALAT
    @GET("inventori/getalat/kategori/{id}")
    Call<ResponseAllAlat> getAllAlat(@Header("Authorization") String authToken,
                                     @Path("id") String id);

    @GET("inventori/getalat/{id}")
    Call<ResponseAlat> getAlat(@Header("Authorization") String authToken,
                               @Path("id") String id);


    // LOGIN
    @FormUrlEncoded
    @POST("login/mobile")
    Call<LoginModel> setLogin(@Header("Authorization") String authToken,
                              @Field("username") String username,
                              @Field("password") String password);


    // DATA ADMIN
    @GET("mobileauth/admin/{id}")
    Call<ResponAdmin> getAdmin(@Header("Authorization") String authToken,
                               @Path("id") String id);


    // DATA DRIVER
    @GET("mobileauth/driver/{id}")
    Call<ResponDriver> getDriver(@Header("Authorization") String authToken,
                                 @Path("id") String id);

    @FormUrlEncoded
    @POST("editdriver/{id}")
    Call<ResponDriver> updateDataDriver(@Header("Authorization") String authToken,
                                        @Field("nama") String nama,
                                        @Field("alamat") String alamat,
                                        @Field("telepon") String telepon,
                                        @Field("email") String email,
                                        @Field("status") String status,
                                        @Field("username") String username,
                                        @Path("id") String id);

    @Multipart
    @POST("editdriver/{id}")
    Call<ResponDriver> updateFotoDriver(@Header("Authorization") String authToken,
                                        @Part("nama") RequestBody nama,
                                        @Part("alamat") RequestBody alamat,
                                        @Part("telepon") RequestBody telepon,
                                        @Part("email") RequestBody email,
                                        @Part("status") RequestBody status,
                                        @Part("username") RequestBody username,
                                        @Part MultipartBody.Part foto,
                                        @Path("id") String id);

    @FormUrlEncoded
    @PUT("driver/changepassword/{id}")
    Call<ResponDriver> updatePasswordDriver(@Header("Authorization") String authToken,
                                            @Field("old_password") String old_password,
                                            @Field("new_password") String new_password,
                                            @Path("id") String id);

    @FormUrlEncoded
    @POST("driver/cekalat/{pesanan_id}")
    Call<ResponDriver> cekAlatDriver(@Header("Authorization") String authToken,
                                     @Field("alat_id") String alat_id,
                                     @Field("jumlah") String jumlah,
                                     @Path("pesanan_id") String pesanan_id);
}
