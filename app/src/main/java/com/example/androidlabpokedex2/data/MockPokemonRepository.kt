package com.example.androidlabpokedex2.data

import com.example.androidlabpokedex2.domain.PokemonRepository
import com.example.androidlabpokedex2.domain.PokemonEntity

class MockPokemonRepository: PokemonRepository {
    val items = mutableListOf<PokemonEntity>(
        PokemonEntity("1", "bulbasaur", generateUrlFromId(1)),
        PokemonEntity("2", "ivysaur", generateUrlFromId(2)),
        PokemonEntity("3", "venusaur", generateUrlFromId(3), 1),
        PokemonEntity("4", "charmander", generateUrlFromId(4),1),
        PokemonEntity("5", "charmeleon", generateUrlFromId(5)),
    )

    override fun getPokemonList(): List<PokemonEntity> = items

    override fun addNewPokemon(pokemon: PokemonEntity) {
        items.add(pokemon)
    }

    private fun generateUrlFromId(id: Int): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}