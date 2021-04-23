package com.example.androidlabpokedex2.di

import com.example.androidlabpokedex2.data.NetworkPokemonRepository
import com.example.androidlabpokedex2.data.network.createPokedexApiService
import com.example.androidlabpokedex2.domain.PokemonRepository

object Injector {
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    fun providePokemonRepository(): PokemonRepository = repository
}