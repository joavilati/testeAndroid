package com.jhonata.catapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.jhonata.catapp.model.Breed
import com.jhonata.catapp.model.StatusDTO
import com.jhonata.catapp.repository.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CatsViewModel"

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val repository: CatsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _breeds = MutableLiveData<List<Breed>>()
    val breeds: LiveData<List<Breed>> get() = _breeds

    private val _loading = MutableLiveData<TURN>()
    val loading: LiveData<TURN> get() = _loading

    private val _error = MutableLiveData<TURN>()
    val error: LiveData<TURN> get() = _error

    fun getBreeds(page: Int) {
        val turn = if(page > 0) TURN.OTHERS else TURN.FIRST
        viewModelScope.launch {
            repository.getBreeds(page).collect { response ->
                when (response.statusDTO) {
                    StatusDTO.SUCCESS -> {
                        _breeds.value = response.data!!
                    }
                    StatusDTO.LOADING -> {
                        _loading.postValue(turn)
                    }
                    StatusDTO.ERROR -> {
                        _error.postValue(turn)
                    }
                }
            }
        }
    }

    enum class TURN {
        FIRST,
        OTHERS
    }
}