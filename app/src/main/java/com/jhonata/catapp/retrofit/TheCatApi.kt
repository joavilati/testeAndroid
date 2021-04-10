package com.jhonata.catapp.retrofit

import com.jhonata.catapp.model.Breed
import retrofit2.Response
import retrofit2.http.GET

interface TheCatApi {

    @GET()
    suspend fun getBreeds(page:Int):Response<List<Breed>>

}