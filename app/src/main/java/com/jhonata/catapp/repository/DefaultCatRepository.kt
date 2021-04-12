package com.jhonata.catapp.repository

import com.jhonata.catapp.model.Breed
import com.jhonata.catapp.model.ResponseDTO
import com.jhonata.catapp.retrofit.TheCatApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
//https://medium.com/android-dev-br/generics-e-variance-em-kotlin-in-out-t-ca5ca07c9fc5
class DefaultCatRepository(
    @Inject val api: TheCatApi
): CatRepository {
    override fun getBreeds(page: Int) = flow {
        emit(ResponseDTO.loading<List<Breed>>())
        try {
            val result =  api.getBreeds(page)
            emit(ResponseDTO.success(result.body()))
        }catch (e: Exception) {
            emit(ResponseDTO.error<List<Breed>>())
        }
    }
}