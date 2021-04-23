package com.example.androidlabpokedex2.presentation.list

import com.example.androidlabpokedex2.presentation.list.adapter.PokemonItem

sealed class PokemonListViewState {
    object LoadingState: PokemonListViewState()
    data class ErrorState(val errorMessage: String): PokemonListViewState()
    data class ContentState(val items: List<PokemonItem>): PokemonListViewState()
}