package com.example.androidlabpokedex2.domain

import io.reactivex.Single

interface PokemonRepository {
    fun getPokemonList(): Single<List<PokemonEntity>>
    fun getPokemonById(id: String): Single<PokemonEntity>
}