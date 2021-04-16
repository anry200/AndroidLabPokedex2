package com.example.androidlabpokedex2.domain

interface PokemonRepository {
//    fun getPokemonList(): List<PokemonEntity>
//    fun addNewPokemon(pokemon: PokemonEntity)
    fun getPokemonList(callback: RepositoryCallback<List<PokemonEntity>>)
    fun getPokemonById(id: String, callback: RepositoryCallback<PokemonEntity>)
}

interface RepositoryCallback<T> {
    fun onSuccess(data: T)
    fun onError(error: String)
}