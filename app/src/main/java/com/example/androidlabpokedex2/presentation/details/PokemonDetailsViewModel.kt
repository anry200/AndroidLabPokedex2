package com.example.androidlabpokedex2.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.domain.Result
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val id: String,
    private val repository: PokemonRepository
) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<PokemonDetailsViewState>()

    fun viewState(): LiveData<PokemonDetailsViewState> = viewStateLiveData

    fun fetch() {
        viewStateLiveData.value = PokemonDetailsViewState.Loading

        viewModelScope.launch {
            viewStateLiveData.value = when (val result = repository.getPokemonById(id)) {
                is Result.Success -> {
                    val pokemonEntity = result.data
                    createContentViewState(pokemonEntity)
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error: ", result.exception)
                    createErrorViewState("Failed to load pokemon with id=$id")
                }
            }
        }
    }

    private fun createContentViewState(pokemonEntity: PokemonEntity) =
        PokemonDetailsViewState.Content(
            name = pokemonEntity.name,
            imageUrl = pokemonEntity.previewUrl,
            abilities = pokemonEntity.abilities
        )

    private fun createErrorViewState(message: String) = PokemonDetailsViewState.Error(message)
}