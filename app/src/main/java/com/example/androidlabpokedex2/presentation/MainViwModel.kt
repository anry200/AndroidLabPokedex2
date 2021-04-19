package com.example.androidlabpokedex2.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlabpokedex2.data.NetworkPokemonRepository
import com.example.androidlabpokedex2.data.network.createPokedexApiService
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.presentation.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.adapter.toItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViwModel: ViewModel() {

    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    private var disposable: Disposable? = null

    private val _pokemonListLiveData = MutableLiveData<List<DisplayableItem>>()
    fun getPokemonList(): LiveData<List<DisplayableItem>> = _pokemonListLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    fun loading(): LiveData<Boolean> = _loadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    fun error(): LiveData<String> = _errorLiveData

    fun loadData() {
        _loadingLiveData.value = true

        disposable = repository.getPokemonList()
            .map { items -> items.map { it.toItem() } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _pokemonListLiveData.postValue(it)
                }, {
                    Log.d("ViewModel", "Error is", it)
                    _errorLiveData.postValue("Error")
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}