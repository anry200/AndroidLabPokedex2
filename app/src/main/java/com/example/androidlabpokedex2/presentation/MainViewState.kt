package com.example.androidlabpokedex2.presentation

import com.example.androidlabpokedex2.presentation.adapter.PokemonItem

sealed class MainViewState {
    object LoadingState: MainViewState()
    data class ErrorState(val errorMessage: String): MainViewState()
    data class ContentState(val items: List<PokemonItem>): MainViewState()
}