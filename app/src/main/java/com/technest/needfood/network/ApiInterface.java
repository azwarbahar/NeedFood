package com.technest.needfood.network;

import com.technest.needfood.models.bahan.ResponseAllBahan;
import com.technest.needfood.models.kategori.ResponKategori;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    // KATEGORI
    @GET("inventori/getkategori/")
    Call<ResponKategori> getKategori(@Header("Authorization") String authToken ,
                                     @Query("kategori") String kategori);

    // BAHAN
    @GET("inventori/getbahan/kategori/{id}")
    Call<ResponseAllBahan> getAllBahan(@Header("Authorization") String authToken ,
                                       @Path("id") String id);



}
