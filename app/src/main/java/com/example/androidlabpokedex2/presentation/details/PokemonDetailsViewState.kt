package com.example.androidlabpokedex2.presentation.details

sealed class PokemonDetailsViewState {
    object Loading : PokemonDetailsViewState()

    data class Content(
        val name: String,
        val imageUrl: String,
        val abilities: List<String>
    ) : PokemonDetailsViewState()

    data class Error(val message: String) : PokemonDetailsViewState()
}