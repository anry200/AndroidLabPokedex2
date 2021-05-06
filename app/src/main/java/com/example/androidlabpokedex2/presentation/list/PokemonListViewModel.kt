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
): ViewModel() {
    private val viewStateLiveData = MutableLiveData<PokemonListViewState>()
    fun viewState(): LiveData<PokemonListViewState> = viewStateLiveData

    fun loadData() {
        viewStateLiveData.value = PokemonListViewState.Loading

        viewModelScope.launch {
            viewStateLiveData.value =  when (val result = repository.getPokemonList()) {
                is Result.Success -> {
                    val pokemonList = result.data
                    PokemonListViewState.Data(pokemonList.map { it.toItem() })
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error is", result.exception)
                    PokemonListViewState.Error("Error Message")
                }
            }
        }
    }
}