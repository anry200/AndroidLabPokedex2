package com.example.androidlabpokedex2.domain

data class PokemonEntity(
    val id: String,
    val name: String,
    val previewUrl: String,
    val abilities: List<String> = emptyList()
)