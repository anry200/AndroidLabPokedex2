package com.example.androidlabpokedex2.presentation.details

sealed class PokemonDetailsViewState {
    object Loading : PokemonDetailsViewState()
    data class Error(val errorMessage: String) : PokemonDetailsViewState()
    data class Content(
        val name: String,
        val imageUrl: String,
        val abilities: List<String>
    ) : PokemonDetailsViewState()
}