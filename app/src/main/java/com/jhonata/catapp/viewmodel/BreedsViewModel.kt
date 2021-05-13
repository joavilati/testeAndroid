package com.jhonata.catapp.viewmodel

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
class BreedsViewModel @Inject constructor(
    private val repository: CatsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var chosenBreed:Breed? = null
    private set

    var positionBreed: Int? = null
    private set

    private val breedList = mutableSetOf<Breed>()
    private val _breeds = MutableLiveData<Set<Breed>>()
    val breeds: LiveData<Set<Breed>> get() = _breeds

    private val _loading = MutableLiveData<TURN>(TURN.NONE)
    val loading: LiveData<TURN> get() = _loading

    private val _error = MutableLiveData<TURN>()
    val error: LiveData<TURN> get() = _error

    fun getBreeds(page: Int) {
        val turn = if(page > 0) TURN.OTHERS else TURN.FIRST
        viewModelScope.launch {
            repository.getBreeds(page).collect { response ->
                when (response.statusDTO) {
                    StatusDTO.SUCCESS -> {
                        breedList.addAll(response.data!!)
                        _breeds.value = breedList
                        _loading.postValue(TURN.NONE)
                    }
                    StatusDTO.LOADING -> {
                        _loading.postValue(turn)
                    }
                    StatusDTO.ERROR -> {
                        _loading.postValue(TURN.NONE)
                        _error.postValue(turn)
                    }
                }
            }
        }
    }

    // Ia fazer um loading direfente para cada estado, mas por questão de tempo vou deixar para depois...
    enum class TURN {
        FIRST,
        OTHERS,
        NONE
    }

    fun setChosenBreed(breed: Breed, position: Int) {
        chosenBreed = breed
        positionBreed = position
    }
}