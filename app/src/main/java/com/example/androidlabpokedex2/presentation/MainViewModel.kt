package com.example.androidlabpokedex2.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlabpokedex2.data.NetworkPokemonRepository
import com.example.androidlabpokedex2.data.network.createPokedexApiService
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.presentation.adapter.HeaderItem
import com.example.androidlabpokedex2.presentation.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.adapter.PokemonItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {
    //private val repository: PokemonRepository = MockPokemonRepository()
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    private var disposable: Disposable? = null

    private val _pokemonListLiveData = MutableLiveData<List<DisplayableItem>>()
    fun getPokemonList(): LiveData<List<DisplayableItem>> = _pokemonListLiveData

    fun loadData() {
        disposable = repository.getPokemonList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    showData(it)
                }, {
                    Log.d("ViewModel", "Error is", it)
                }
            )
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