package com.jhonata.catapp.retrofit

import com.jhonata.catapp.model.Breed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheCatApi {

    @GET("breeds")
    suspend fun getBreeds(
        @Query("page") page:Int,
        @Query("limit") limit: Int = 15,
        @Query("order") order:String = "asc"
    ):Response<List<Breed>>

}