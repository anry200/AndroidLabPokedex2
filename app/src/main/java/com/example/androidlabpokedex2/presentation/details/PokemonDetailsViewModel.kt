package com.example.androidlabpokedex2.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlabpokedex2.di.Injector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class PokemonDetailsViewModel: ViewModel() {
    private val repository = Injector.providePokemonRepository()
    private var disposable: Disposable? = null
    private val viewStateLiveData = MutableLiveData<PokemonDetailsViewState>()

    fun viewState(): LiveData<PokemonDetailsViewState> = viewStateLiveData

    fun loadPokemonById(id: String) {
        viewStateLiveData.value = PokemonDetailsViewState.Loading

        disposable = repository.getPokemonById(id)
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pokemonEntity ->
                viewStateLiveData.value = PokemonDetailsViewState.Data(
                    name = pokemonEntity.name,
                    imageUrl = pokemonEntity.previewUrl,
                    abilities = pokemonEntity.abilities
                )
            }, {
                viewStateLiveData.value = PokemonDetailsViewState.Error("Failed to load pokemon with id=$id")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}