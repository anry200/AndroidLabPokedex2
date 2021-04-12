package com.example.androidlabpokedex2.domain

data class PokemonEntity(
    val id: String,
    val name: String,
    val previewUrl: String,
    val generation: Int = 0,
    val abilities: List<String> = emptyList()
)