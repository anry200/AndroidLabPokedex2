package com.example.androidlabpokedex2.data

import com.example.androidlabpokedex2.data.network.PokedexApiService
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.example.androidlabpokedex2.domain.PokemonRepository
import io.reactivex.Observable
import io.reactivex.Single

class NetworkPokemonRepository(
    val api: PokedexApiService
): PokemonRepository {
    override fun getPokemonList(): Single<List<PokemonEntity>> {
        return api.fetchPokemonList()
            .flatMap { pokemonList ->
                Observable.fromIterable(pokemonList.results)
                    .flatMapSingle {
                        getPokemonById(it.name)
                    }.toList()
            }
    }

    override fun getPokemonById(id: String): Single<PokemonEntity> {
        return api.fetchPokemonInfo(id)
            .map { serverPokemon ->
                val abilities = serverPokemon.abilities.map { it.ability.name }

                PokemonEntity(
                    id = serverPokemon.id,
                    name = serverPokemon.name,
                    previewUrl = generateUrlFromId(serverPokemon.id),
                    abilities = abilities)
            }
    }

    fun generateUrlFromId(id: String): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}