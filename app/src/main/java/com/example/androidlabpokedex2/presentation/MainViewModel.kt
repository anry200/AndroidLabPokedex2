package com.example.androidlabpokedex2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlabpokedex2.data.NetworkPokemonRepository
import com.example.androidlabpokedex2.data.network.createPokedexApiService
import com.example.androidlabpokedex2.domain.RepositoryCallback
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.presentation.adapter.HeaderItem
import com.example.androidlabpokedex2.presentation.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.adapter.PokemonItem

class MainViewModel: ViewModel() {
    //private val repository: PokemonRepository = MockPokemonRepository()
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    private val _pokemonListLiveData = MutableLiveData<List<DisplayableItem>>()
    fun getPokemonList(): LiveData<List<DisplayableItem>> = _pokemonListLiveData

    fun loadData() {
        repository.getPokemonList(object : RepositoryCallback<List<PokemonEntity>> {
            override fun onSuccess(data: List<PokemonEntity>) {
                showData(data)
            }

            override fun onError(error: String) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun showData(pokemons: List<PokemonEntity>) {
        val generation0 = pokemons.filter { it.generation == 0 }
            .map { it.toItem() }
        val generation1 = pokemons.filter { it.generation == 1 }
            .map { it.toItem() }

        val newList = mutableListOf<DisplayableItem>()
        newList.add(HeaderItem("Generation 0"))
        newList.addAll(generation0)
        newList.add(HeaderItem("Generation 1"))
        newList.addAll(generation1)

        _pokemonListLiveData.postValue(newList)
    }

    private fun PokemonEntity.toItem(): PokemonItem =
        PokemonItem(id, name, previewUrl)
}