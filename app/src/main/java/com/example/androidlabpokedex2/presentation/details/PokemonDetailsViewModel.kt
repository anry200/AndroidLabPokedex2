package com.example.androidlabpokedex2.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.domain.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val repository: PokemonRepository
) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<PokemonDetailsViewState>()

    fun viewState(): LiveData<PokemonDetailsViewState> = viewStateLiveData

    fun loadPokemonById(id: String) {
        viewStateLiveData.value = PokemonDetailsViewState.Loading

        fun PokemonEntity.toDataViewState() = PokemonDetailsViewState.Data(
            name = name,
            imageUrl = previewUrl,
            abilities = abilities
        )

        viewModelScope.launch {
            delay(2000)
            viewStateLiveData.value = when (val result = repository.getPokemonById(id)) {
                is Result.Success -> {
                    val responseData = result.data
                    responseData.toDataViewState()
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error is", result.exception)
                    createErrorViewState("Failed to load pokemon with id=$id")
                }
            }
        }
    }

    private fun createErrorViewState(message: String) = PokemonDetailsViewState.Error(message)
}