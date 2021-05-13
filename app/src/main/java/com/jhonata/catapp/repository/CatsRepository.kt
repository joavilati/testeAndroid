package com.jhonata.catapp.repository

import com.jhonata.catapp.model.Breed
import com.jhonata.catapp.model.ResponseDTO
import kotlinx.coroutines.flow.Flow

interface CatsRepository {
    fun getBreeds(page:Int): Flow<ResponseDTO<out List<Breed>?>>
}