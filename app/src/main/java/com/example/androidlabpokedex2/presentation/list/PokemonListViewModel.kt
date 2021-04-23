package com.example.androidlabpokedex2.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidlabpokedex2.di.Injector
import com.example.androidlabpokedex2.presentation.list.adapter.toItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PokemonListViewModel: ViewModel() {
    private val repository = Injector.providePokemonRepository()

    private var disposable: Disposable? = null

    private val viewStateLiveData = MutableLiveData<PokemonListViewState>()
    fun viewState(): LiveData<PokemonListViewState> = viewStateLiveData

    fun loadData() {
        viewStateLiveData.value = PokemonListViewState.LoadingState

        disposable = repository.getPokemonList()
            .map { items -> items.map { it.toItem() } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewStateLiveData.value = PokemonListViewState.ContentState(it)
                }, {
                    Log.d("ViewModel", "Error is", it)
                    viewStateLiveData.value = PokemonListViewState.ErrorState("Error Message")
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}