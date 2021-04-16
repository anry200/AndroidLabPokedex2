package com.example.androidlabpokedex2.data

import com.example.androidlabpokedex2.data.network.PokedexApiService
import com.example.androidlabpokedex2.data.network.PokemonDetailedResponse
import com.example.androidlabpokedex2.data.network.PokemonListResponse
import com.example.androidlabpokedex2.data.network.PokemonPartialResponse
import com.example.androidlabpokedex2.domain.RepositoryCallback
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.example.androidlabpokedex2.domain.PokemonRepository
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkPokemonRepository(
    val api: PokedexApiService
): PokemonRepository {
    override fun getPokemonList(callback: RepositoryCallback<List<PokemonEntity>>) {
        api.fetchPokemonList().enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(call: Call<PokemonListResponse>, response: Response<PokemonListResponse>) {
                val pokemonListResponse: PokemonListResponse? = response.body()

                if (response.isSuccessful && pokemonListResponse != null) {
                    val pokemonList = pokemonListResponse.results.map {
                        PokemonEntity(it.name, it.name, generateUrlFromId(it.name))
                    }
                    callback.onSuccess(pokemonList)
                } else {
                    callback.onError("Empty response")
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                callback.onError("Empty response")
            }
        })
    }

    override fun getPokemonById(id: String): Single<PokemonEntity> {

        return Single.create { emitter ->
            api.fetchPokemonInfo(id).enqueue(object : Callback<PokemonDetailedResponse> {
                override fun onResponse(call: Call<PokemonDetailedResponse>, response: Response<PokemonDetailedResponse>) {
                    val serverPokemon: PokemonDetailedResponse? = response.body()

                    if (serverPokemon == null) {
                        if (!emitter.isDisposed) {
                            emitter.onError(Throwable("Empty response"))
                        }
                    } else {
                        val abilities = serverPokemon.abilities.map { it.ability.name }

                        val pokemonEntity = PokemonEntity(
                            id = serverPokemon.id,
                            name = serverPokemon.name,
                            previewUrl = generateUrlFromId(serverPokemon.id),
                            abilities = abilities)
                        if (!emitter.isDisposed) {
                            emitter.onSuccess(pokemonEntity)
                        }
                    }
                }

                override fun onFailure(call: Call<PokemonDetailedResponse>, t: Throwable) {
                    if (!emitter.isDisposed) {
                        emitter.onError(Throwable("Empty response"))
                    }
                }
            })
        }
    }

    fun generateUrlFromId(id: String): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

}