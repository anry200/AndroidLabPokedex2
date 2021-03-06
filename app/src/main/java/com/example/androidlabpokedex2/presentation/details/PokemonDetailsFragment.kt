package com.example.androidlabpokedex2.presentation.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.databinding.FragmentPokemonDetailsBinding
import com.example.androidlabpokedex2.presentation.utils.load
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {
    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by viewModel { parametersOf(navArgs.pokemonId) }
    private val viewBinding: FragmentPokemonDetailsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState().observe(viewLifecycleOwner, ::showViewState)
        viewModel.fetch()
    }

    private fun showViewState(viewState: PokemonDetailsViewState) = viewBinding.apply {
        when (viewState) {
            PokemonDetailsViewState.Loading -> {
                loadingViewGroup.isVisible = true
                contentGroup.isVisible = false
                errorMessage.isVisible = false
            }
            is PokemonDetailsViewState.Content -> {
                loadingViewGroup.isVisible = false
                contentGroup.isVisible = true
                errorMessage.isVisible = false

                name.text = viewState.name
                abilities.text = viewState.abilities.formatByCommas()
                image.load(viewState.imageUrl)
            }
            is PokemonDetailsViewState.Error -> {
                loadingViewGroup.isVisible = false
                contentGroup.isVisible = true
                errorMessage.isVisible = false

                errorMessage.text = viewState.errorMessage
            }
        }
    }

    private fun List<String>.formatByCommas(): String = joinToString(separator = ",") { it }
}