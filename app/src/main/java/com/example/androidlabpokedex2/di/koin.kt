package com.example.androidlabpokedex2.di

import com.example.androidlabpokedex2.data.NetworkPokemonRepository
import com.example.androidlabpokedex2.data.network.PokedexApiService
import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.presentation.details.PokemonDetailsViewModel
import com.example.androidlabpokedex2.presentation.list.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<PokedexApiService> { createPokedexApiService() }
    single<PokemonRepository> { NetworkPokemonRepository(get()) }

    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonDetailsViewModel(get()) }
}

private fun createPokedexApiService(): PokedexApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(PokedexApiService::class.java)
}