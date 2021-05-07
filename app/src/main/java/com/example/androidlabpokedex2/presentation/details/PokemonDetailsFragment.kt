package com.example.androidlabpokedex2.presentation.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.databinding.FragmentPokemonDetailsBinding
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {
    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by viewModel { parametersOf(navArgs.pokemonId) }
    private val viewBinding: FragmentPokemonDetailsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState().observe(viewLifecycleOwner, ::showViewState)
        viewModel.loadPokemon()
    }

    private fun showViewState(viewState: PokemonDetailsViewState) = viewBinding.apply {
        when (viewState) {
            PokemonDetailsViewState.Loading -> {
                progressView.isVisible = true
                contentGroup.isVisible = false
                errorMessage.isVisible = false
            }
            is PokemonDetailsViewState.Content -> {
                progressView.isVisible = false
                contentGroup.isVisible = true
                errorMessage.isVisible = false

                showContent(viewState)
            }
            is PokemonDetailsViewState.Error -> {
                progressView.isVisible = false
                contentGroup.isVisible = false
                errorMessage.isVisible = true
            }
        }
    }

    private fun showContent(state: PokemonDetailsViewState.Content) = viewBinding.apply {
        name.text = state.name
        abilities.text = state.abilities.joinToString(separator = ",") { it }
        Picasso.get().load(state.imageUrl).into(image)
    }
}