package com.example.androidlabpokedex2.domain

interface PokemonRepository {
    suspend fun getPokemonList(): Result<List<PokemonEntity>>
    suspend fun getPokemonById(id: String): Result<PokemonEntity>
}