package com.example.androidlabpokedex2.presentation

import android.util.Log
import com.example.androidlabpokedex2.data.NetworkPokemonRepository
import com.example.androidlabpokedex2.data.network.createPokedexApiService
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.presentation.adapter.toItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter {
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    private var view: MainView? = null
    private var disposable: Disposable? = null

    fun loadData() {
        view?.showProgress()

        disposable = repository.getPokemonList()
            .map { items -> items.map { it.toItem() } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.showData(it)
                }, {
                    Log.d("ViewModel", "Error is", it)
                    view?.showError("Failed to load data")
                }
            )
    }

    fun attachView(view: MainView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
        disposable?.dispose()
    }
}