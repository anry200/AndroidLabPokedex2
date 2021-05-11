package com.example.androidlabpokedex2.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.domain.Result
import com.example.androidlabpokedex2.presentation.list.adapter.toItem
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val repository: PokemonRepository
) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<PokemonListViewState>()
    fun viewState(): LiveData<PokemonListViewState> = viewStateLiveData

    fun fetch() {
        viewStateLiveData.value = PokemonListViewState.Loading

        viewModelScope.launch {
            viewStateLiveData.value = when (val result = repository.getPokemonList()) {
                is Result.Success -> {
                    val pokemonEntityList = result.data
                    PokemonListViewState.Content(
                        items = pokemonEntityList.map { pokemonEntity -> pokemonEntity.toItem() }
                    )
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error: ", result.exception)
                    PokemonListViewState.Error("Network Error")
                }
            }
        }
    }
}