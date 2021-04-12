package com.example.androidlabpokedex2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlabpokedex2.data.MockPokemonRepository
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.presentation.adapter.HeaderItem
import com.example.androidlabpokedex2.presentation.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.adapter.PokemonItem

class MainViewModel: ViewModel() {
    private val repository: PokemonRepository = MockPokemonRepository()

    private val _pokemonListLiveData = MutableLiveData<List<DisplayableItem>>()
    fun getPokemonList(): LiveData<List<DisplayableItem>> = _pokemonListLiveData

    fun loadData() {
        val pokemons = repository.getPokemonList()

        val generation0 = pokemons.filter { it.generation == 0 }
            .map { it.toItem() }
        val generation1 = pokemons.filter { it.generation == 1 }
            .map { it.toItem() }

        val newList = mutableListOf<DisplayableItem>()
        newList.add(HeaderItem("Generation 0"))
        newList.addAll(generation0)
        newList.add(HeaderItem("Generation 1"))
        newList.addAll(generation1)

        _pokemonListLiveData.value = newList
    }

    private fun PokemonEntity.toItem(): PokemonItem =
        PokemonItem(id, name, previewUrl)
}