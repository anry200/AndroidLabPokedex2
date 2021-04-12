package com.example.androidlabpokedex2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlabpokedex2.data.MockPokemonRepository
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.presentation.adapter.BannerItem
import com.example.androidlabpokedex2.presentation.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.adapter.PokemonItem

class MainViewModel: ViewModel() {
    private val repository: PokemonRepository = MockPokemonRepository()

    private val _pokemonListLiveData = MutableLiveData<List<DisplayableItem>>()
    fun getPokemonList(): LiveData<List<DisplayableItem>> = _pokemonListLiveData

    fun loadData() {
        val pokemons = repository.getPokemonList()

        val pokemonItemList = pokemons.mapIndexed { index, pokemon ->
            val userColor = index % 4 == 0
            PokemonItem(pokemon.id, pokemon.name, pokemon.previewUrl, userColor)
        }

        val banner = BannerItem("Banner")

        val newList = mutableListOf<DisplayableItem>()
        newList.add(banner)
        newList.addAll(pokemonItemList)

        _pokemonListLiveData.value = newList
    }
}