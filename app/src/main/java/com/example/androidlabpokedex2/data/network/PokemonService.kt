package com.example.androidlabpokedex2.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

fun createPokedexApiService(): PokedexApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(PokedexApiService::class.java)
}

interface PokedexApiService {
    /**
     * See for details: https://pokeapi.co/api/v2/pokemon
     */
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    /**
     * See for details: https://pokeapi.co/api/v2/pokemon/bulbasaur
     */
    @GET("pokemon/{name}")
    suspend fun fetchPokemonDetails(@Path("name") name: String): PokemonDetailsResponse
}

data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonPartialResponse>,
)

data class PokemonPartialResponse(
    val name: String,
    val url: String
)

data class PokemonDetailsResponse(
    val id: String,
    val name: String,
    val abilities: List<PokemonAbilityData>
)

data class PokemonAbilityDetailsData(
    val name: String,
    val url: String
)

data class PokemonAbilityData(
    val ability: PokemonAbilityDetailsData,
    val is_hidden: Boolean,
    val slot: Int,
)
