package com.example.androidlabpokedex2.presentation.list.adapter

import com.example.androidlabpokedex2.domain.PokemonEntity

interface DisplayableItem

data class PokemonItem(
    val id: String,
    val name: String,
    val image: String,
    val useRedColor: Boolean = false,
): DisplayableItem

data class HeaderItem(
    val text: String
): DisplayableItem

fun PokemonEntity.toItem() = PokemonItem(id, name, previewUrl)