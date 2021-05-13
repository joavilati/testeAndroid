package com.jhonata.catapp.repository

import com.jhonata.catapp.model.Breed
import com.jhonata.catapp.model.ResponseDTO
import com.jhonata.catapp.retrofit.TheCatApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
//https://medium.com/android-dev-br/generics-e-variance-em-kotlin-in-out-t-ca5ca07c9fc5
class DefaultCatsRepository @Inject constructor(
    private val api: TheCatApi
): CatsRepository {
    override fun getBreeds(page: Int) = flow {
        emit(ResponseDTO.loading<List<Breed>>())
        try {
            val result =  api.getBreeds(page = page)
            result.run {
                if(isSuccessful) {
                    emit(ResponseDTO.success(body()))
                } else {
                    emit(ResponseDTO.error<List<Breed>>(message = message(), code = code()))
                }
            }

        } catch (e: Exception) {
            emit(ResponseDTO.error<List<Breed>>(e))
        }
    }.flowOn(Dispatchers.IO)
}