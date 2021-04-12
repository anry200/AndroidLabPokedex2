package com.example.androidlabpokedex2.domain

interface PokemonRepository {
    fun getPokemonList(): List<PokemonEntity>
    fun addNewPokemon(pokemon: PokemonEntity)
}