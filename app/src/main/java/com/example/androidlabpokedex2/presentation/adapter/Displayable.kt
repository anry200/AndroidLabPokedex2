package com.example.androidlabpokedex2.presentation.adapter

interface DisplayableItem

data class PokemonItem(
    val id: String,
    val name: String,
    val image: String,
    val useRedColor: Boolean = false,
): DisplayableItem

data class BannerItem(
    val bannerText: String
): DisplayableItem