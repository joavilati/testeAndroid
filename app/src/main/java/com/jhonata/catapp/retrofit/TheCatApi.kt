package com.jhonata.catapp.retrofit

import com.jhonata.catapp.model.Breed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheCatApi {

    @GET("breed")
    suspend fun getBreeds(
        @Query("order") order:String = "asc",
        @Query("page") page:Int,
        @Query("limit") limit: Int = 15
    ):Response<List<Breed>>

}