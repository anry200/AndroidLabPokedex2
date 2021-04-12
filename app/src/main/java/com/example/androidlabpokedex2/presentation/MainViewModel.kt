package com.example.androidlabpokedex2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlabpokedex2.data.MockPokemonRepository
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.example.androidlabpokedex2.domain.PokemonRepository

class MainViewModel: ViewModel() {
    private val repository: PokemonRepository = MockPokemonRepository()

    private val _pokemonListLiveData = MutableLiveData<List<PokemonEntity>>()
    fun getPokemonList(): LiveData<List<PokemonEntity>> = _pokemonListLiveData

    fun loadData() {
        val pokemons = repository.getPokemonList()
        _pokemonListLiveData.value = pokemons
    }
}