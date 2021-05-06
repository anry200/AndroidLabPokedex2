package com.example.androidlabpokedex2.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlabpokedex2.di.Injector
import com.example.androidlabpokedex2.domain.Result
import com.example.androidlabpokedex2.presentation.list.adapter.toItem
import kotlinx.coroutines.launch

class PokemonListViewModel: ViewModel() {
    private val repository = Injector.providePokemonRepository()


    private val viewStateLiveData = MutableLiveData<PokemonListViewState>()
    fun viewState(): LiveData<PokemonListViewState> = viewStateLiveData

    fun loadData() {
        viewStateLiveData.value = PokemonListViewState.Loading

        viewModelScope.launch {
            viewStateLiveData.value =  when (val result = repository.getPokemonList()) {
                is Result.Success -> {
                    val pokemonItems = result.data.map { it.toItem() }
                    PokemonListViewState.Data(pokemonItems)
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error is", result.exception)
                    PokemonListViewState.Error("Error Message")
                }
            }
        }
    }
}