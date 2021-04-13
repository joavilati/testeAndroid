package com.jhonata.catapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jhonata.catapp.repository.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val repository: CatsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getBreeds(page:Int) = repository.getBreeds(page)

}