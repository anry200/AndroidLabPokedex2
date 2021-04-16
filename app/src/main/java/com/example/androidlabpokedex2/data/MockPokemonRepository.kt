package com.example.androidlabpokedex2.data

import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.domain.PokemonEntity
import io.reactivex.Single

class MockPokemonRepository: PokemonRepository {
    val items = mutableListOf<PokemonEntity>(
        PokemonEntity("1", "bulbasaur", generateUrlFromId(1)),
        PokemonEntity("2", "ivysaur", generateUrlFromId(2)),
        PokemonEntity("3", "venusaur", generateUrlFromId(3), 1),
        PokemonEntity("4", "charmander", generateUrlFromId(4),1),
        PokemonEntity("5", "charmeleon", generateUrlFromId(5)),
    )

    override fun getPokemonList(): Single<List<PokemonEntity>> =
        Single.just(items)

    override fun getPokemonById(id: String): Single<PokemonEntity> {
        val pokemon = items.find { it.id == id }

        return if (pokemon != null) {
            Single.just(pokemon)
        } else {
            Single.error(Throwable("Not found"))
        }
    }

    fun generateUrlFromId(id: Int): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}